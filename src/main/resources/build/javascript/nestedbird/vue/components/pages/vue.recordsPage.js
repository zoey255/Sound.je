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
// @flow
// Node Modules
import Vue from "vue/dist/vue";

// Site Modules

/**
 * This class controls the records page
 * @copyright Copyright (C) 2017  Michael Haddon
 * @author Michael Haddon
 * @since 23 Feb 2017
 * @namespace
 * @readonly
 * @memberOf module:Vue/Components
 */
export const RecordsPage = {
    /**
     * The module template
     * @member module:Vue/Components.RecordsPage#template
     * @default #newsPage-template
     * @type string
     */
    template: `#recordsPage-template`,
    props:    [],
    data(): Object {
        return {
            /**
             * The record elements that can be edited
             * @member module:Vue/Components.RecordsPage#Records
             * @type string[]
             */
            Records: [
                `Events`,
                `EventTimes`,
                `Locations`,
                `Artists`,
                `Songs`,
                `Media`,
                `ScannedPages`,
                `Users`,
                `Privileges`,
                `Roles`,
                `Tags`
            ]
        };
    }
};

export const VueRecordsPage = Vue.extend(RecordsPage);