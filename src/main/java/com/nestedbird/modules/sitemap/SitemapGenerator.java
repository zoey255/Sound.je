/*
 *  NestedBird  Copyright (C) 2016-2017  Michael Haddon
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License version 3
 *  as published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nestedbird.modules.sitemap;

import com.nestedbird.config.ServerConfigSettings;
import com.nestedbird.models.artist.ArtistRepository;
import com.nestedbird.models.core.Audited.AuditedEntity;
import com.nestedbird.models.event.Event;
import com.nestedbird.models.event.EventRepository;
import com.nestedbird.models.location.LocationRepository;
import com.nestedbird.models.medium.MediumRepository;
import com.nestedbird.models.song.SongRepository;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This generates a sitemap for the websites major resources.
 * I dont want to bother with actually using a POJO to XML system because of how much of a pain and a headache
 * XML can be.
 * So i am just manually creating it with strings.
 */
@Component
public class SitemapGenerator {
    private final SongRepository songRepository;

    private final MediumRepository mediumRepository;

    private final EventRepository eventRepository;

    private final LocationRepository locationRepository;

    private final ArtistRepository artistRepository;

    private final ServerConfigSettings serverConfigSettings;

    /**
     * Instantiates a new Sitemap.
     *
     * @param songRepository       the song repository
     * @param mediumRepository     the medium repository
     * @param eventRepository      the event repository
     * @param locationRepository   the location repository
     * @param artistRepository     the artist repository
     * @param serverConfigSettings the server config settings
     */
    @Autowired
    public SitemapGenerator(final SongRepository songRepository,
                            final MediumRepository mediumRepository,
                            final EventRepository eventRepository,
                            final LocationRepository locationRepository,
                            final ArtistRepository artistRepository,
                            final ServerConfigSettings serverConfigSettings) {
        this.songRepository = songRepository;
        this.mediumRepository = mediumRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.artistRepository = artistRepository;
        this.serverConfigSettings = serverConfigSettings;
    }

    /**
     * Generates the XML in string format
     *
     * @return the xml
     */
    public String generate() {
        // @formatter:off
        return StringUtils.join(new String[]{
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">",
                    retrieveEntityXML(),
                "</urlset>"
        }, "");
        // @formatter:on
    }

    private String retrieveEntityXML() {
        final Stream<String> songStream = songRepository.findAll().stream()
                .filter(AuditedEntity::getActive)
                .map(this::generateEntity);

        final Stream<String> mediumStream = mediumRepository.findAll().stream()
                .filter(AuditedEntity::getActive)
                .map(this::generateEntity);

        final Stream<String> eventStream = eventRepository.findAll().stream()
                .filter(AuditedEntity::getActive)
                .filter(Event::isInFuture)
                .map(this::generateEntity);

        final Stream<String> locationStream = locationRepository.findAll().stream()
                .filter(AuditedEntity::getActive)
                .map(this::generateEntity);

        final Stream<String> artistStream = artistRepository.findAll().stream()
                .filter(AuditedEntity::getActive)
                .map(this::generateEntity);

        return concatStreams(extraEntities(), songStream, mediumStream, eventStream, locationStream, artistStream)
                .collect(Collectors.joining());
    }

    @SafeVarargs
    private final Stream<String> concatStreams(final Stream<String>... streams) {
        if (streams.length == 0)
            throw new IllegalArgumentException("No stream provided");

        Stream<String> currentStream = streams[0];
        for (Integer i = 1; i < streams.length; i++) {
            currentStream = Stream.concat(currentStream, streams[i]);
        }
        return currentStream;
    }

    private Stream<String> extraEntities() {
        final List<SitemapEntity> extraEntities = new ArrayList<>();

        extraEntities.add(SitemapEntity.builder()
                .externalUrl(serverConfigSettings.getExternalUrl())
                .relativeUrl("/")
                .lastModified(DateTime.now().withHourOfDay(1).withMinuteOfHour(1).withSecondOfMinute(1))
                .changeFrequency("daily")
                .priority(1.0)
                .build());

        extraEntities.add(SitemapEntity.builder()
                .externalUrl(serverConfigSettings.getExternalUrl())
                .relativeUrl("/Media")
                .lastModified(DateTime.now().withDayOfMonth(1).withHourOfDay(1).withMinuteOfHour(1).withSecondOfMinute(1))
                .changeFrequency("weekly")
                .priority(0.8)
                .build());

        extraEntities.add(SitemapEntity.builder()
                .externalUrl(serverConfigSettings.getExternalUrl())
                .relativeUrl("/News")
                .lastModified(DateTime.now().withDayOfMonth(1).withHourOfDay(1).withMinuteOfHour(1).withSecondOfMinute(1))
                .changeFrequency("weekly")
                .priority(0.8)
                .build());

        extraEntities.add(SitemapEntity.builder()
                .externalUrl(serverConfigSettings.getExternalUrl())
                .relativeUrl("/About")
                .lastModified(DateTime.now().withDayOfMonth(1).withHourOfDay(1).withMinuteOfHour(1).withSecondOfMinute(1))
                .changeFrequency("monthly")
                .priority(0.8)
                .build());

        extraEntities.add(SitemapEntity.builder()
                .externalUrl(serverConfigSettings.getExternalUrl())
                .relativeUrl("/api")
                .lastModified(DateTime.now().withDayOfMonth(1).withHourOfDay(1).withMinuteOfHour(1).withSecondOfMinute(1))
                .changeFrequency("monthly")
                .priority(0.8)
                .build());

        return extraEntities.stream().map(SitemapEntity::generate);
    }

    private String generateEntity(final AuditedEntity e) {
        return SitemapEntity.builder()
                .externalUrl(serverConfigSettings.getExternalUrl())
                .relativeUrl(e.getUrl())
                .lastModified(e.getLastModifiedDate())
                .changeFrequency("monthly")
                .priority(0.8)
                .build()
                .generate();
    }
}
