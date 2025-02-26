/*
 * Copyright 2022 ODK
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.javarosa.core.model.instance.geojson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.javarosa.test.utils.ResourcePathHelper.r;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.javarosa.core.model.instance.TreeElement;
import org.junit.Test;

public class GeoJsonExternalInstanceTest {
    @Test
    public void parse_throwsException_ifNoTopLevelObject() {
        try {
            GeoJsonExternalInstance.parse("id", r("not-object.geojson").toString());
            fail("Exception expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void parse_throwsException_ifTopLevelObjectTypeNotFeatureCollection() {
        try {
            GeoJsonExternalInstance.parse("id", r("invalid-type.geojson").toString());
            fail("Exception expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void parse_throwsException_ifNoFeaturesArray() {
        try {
            GeoJsonExternalInstance.parse("id", r("bad-futures-collection.geojson").toString());
            fail("Exception expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void parse_throwsException_ifFeaturesNotArray() {
        try {
            GeoJsonExternalInstance.parse("id", r("bad-features-not-array.geojson").toString());
            fail("Exception expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void parse_addsGeometriesAsChildren_forMultipleFeatures() throws IOException {
        TreeElement featureCollection = GeoJsonExternalInstance.parse("id", r("feature-collection.geojson").toString());
        assertThat(featureCollection.getNumChildren(), is(2));
        assertThat(featureCollection.getChildAt(0).getChild("geometry", 0).getValue().getValue(), is("102 0.5 0 0"));
        assertThat(featureCollection.getChildAt(1).getChild("geometry", 0).getValue().getValue(), is("104 0.5 0 0"));
    }

    @Test
    public void parse_addsAllOtherPropertiesAsChildren() throws IOException {
        TreeElement featureCollection = GeoJsonExternalInstance.parse("id", r("feature-collection.geojson").toString());
        assertThat(featureCollection.getChildAt(0).getNumChildren(), is(4));
        assertThat(featureCollection.getChildAt(0).getChild("name", 0).getValue().getValue(), is("My cool point"));

        assertThat(featureCollection.getChildAt(1).getNumChildren(), is(5));
        assertThat(featureCollection.getChildAt(1).getChild("special-property", 0).getValue().getValue(), is("special value"));
    }

    @Test
    public void parse_throwsException_whenGeometryNotPoint() {
        try {
            GeoJsonExternalInstance.parse("id", r("feature-collection-with-line.geojson").toString());
            fail("Exception expected");
        } catch (IOException e) {
            // expected
        }
    }
}
