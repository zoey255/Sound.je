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

package com.nestedbird.config;

import com.nestedbird.models.core.DataObject;
import lombok.*;

import java.io.Serializable;

/**
 * This POJO contains the social media configuration settings
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialConfigSettings extends DataObject implements Serializable {
    /**
     * Should this application support the ability to automatically post events to facebook
     */
    private final Boolean fbAutoPost;

    /**
     * Whether the application will automatically scan FB
     */
    private final Boolean fbScan;

    /**
     * Facebook API Key
     */
    private final String fbAccessToken;

    /**
     * Youtube API Key
     */
    private final String ytKey;

    /**
     * Soundcloud API Key
     */
    private final String scClientId;
}
