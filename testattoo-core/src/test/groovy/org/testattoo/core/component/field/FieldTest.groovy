/**
 * Copyright © 2018 Ovea (d.avenante@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.testattoo.core.component.field

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.testattoo.core.component.Component
import org.testattoo.core.support.property.InputSupport
import org.testattoo.core.support.property.LabelSupport
import org.testattoo.core.support.property.LengthSupport
import org.testattoo.core.support.property.ValueSupport
import org.testattoo.core.support.state.FocusSupport
import org.testattoo.core.support.state.RangeSupport
import org.testattoo.core.support.state.ValiditySupport

import static org.mockito.Mockito.spy

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@DisplayName("Field")
class FieldTest {
    @Test
    @DisplayName("Should have expected inheritance")
    void inheritance() {
        assert Field in Component
        assert Field in LabelSupport
        assert Field in InputSupport
        assert Field in ValueSupport
        assert Field in ValiditySupport
        assert Field in FocusSupport

        spy(ColorField)
        assert ColorField in Field

        spy(DateField)
        assert DateField in Field
        assert DateField in RangeSupport

        spy(DateTimeField)
        assert DateTimeField in Field

        spy(EmailField)
        assert EmailField in Field

        spy(MonthField)
        assert MonthField in Field

        spy(NumberField)
        assert NumberField in Field
        assert NumberField in RangeSupport

        spy(PasswordField)
        assert PasswordField in Field
        assert PasswordField in LengthSupport

        spy(TextField)
        assert TextField in Field
        assert TextField in LengthSupport

        spy(PhoneField)
        assert PhoneField in Field

        spy(RangeField)
        assert RangeField in Field
        assert RangeField in RangeSupport

        spy(SearchField)
        assert SearchField in Field
        assert SearchField in LengthSupport

        spy(TimeField)
        assert TimeField in Field

        spy(URLField)
        assert URLField in Field
        assert URLField in LengthSupport

        spy(WeekField)
        assert WeekField in Field
    }
}
