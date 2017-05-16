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
/* eslint func-names:0 */
/* eslint prefer-arrow-callback:0 */

const Ajax = require(`${__base}core/Ajax`);

describe(`Ajax`, function () {
    describe(`#addEvent`, function () {
        it(`should be able to request data from google.com`, function (done) {
            const ajax = new Ajax(`https://www.google.com`);
            ajax.onSuccess(() => {
                done();
            }).onFailure(() => {
                done(`failed to save`);
            });
        });
    });
});