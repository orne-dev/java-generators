package dev.orne.test.rnd.it;

import java.time.OffsetDateTime;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2022 Orne Developments
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import dev.orne.test.rnd.AbstractGenerator;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.generators.EnumGenerator;

/**
 * Custom generator for an enumeration type that prevents generation of an
 * undesired value.
 * <p>
 * Registered through {@code META-INF/services/dev.orne.test.rnd.Generator}
 * SPI.
 * <p>
 * Generator has no {@code Priority} annotation, so gets the default priority
 * ({@value dev.orne.test.rnd.Priority#DEFAULT}), which is greater than built in
 * enumeration values generator
 * ({@value dev.orne.test.rnd.Priority#GENERIC_GENERATORS}) and therefore
 * takes precedence for generation of the supported types.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see Priority
 * @see EnumGenerator
 */
public class CustomBasicGenerator
extends AbstractGenerator {

    @Override
    public boolean supports(@NotNull Class<?> type) {
        return BasicInterface.class.equals(type)
                || BasicImpl.class.equals(type)
                || ExtendedImpl.class.equals(type);
    }

    @Override
    public <T> @NotNull T defaultValue(@NotNull Class<T> type) {
        assertSupported(type);
        final BasicImpl result;
        if (ExtendedImpl.class.equals(type)) {
            result = new ExtendedImpl();
        } else {
            result = new BasicImpl();
        }
        return type.cast(result);
    }

    @Override
    public <T> @NotNull T randomValue(@NotNull Class<T> type) {
        assertSupported(type);
        final BasicImpl result;
        if (ExtendedImpl.class.equals(type)) {
            final ExtendedImpl bean = new ExtendedImpl();
            bean.setCreationDate(Generators.randomValue(OffsetDateTime.class));
            bean.setUpdateDate(Generators.randomValue(OffsetDateTime.class));
            result = bean;
        } else {
            result = new BasicImpl();
        }
        result.setCode(RandomUtils.nextInt(1, Integer.MAX_VALUE));
        result.setName(Generators.randomValue(String.class));
        return type.cast(result);
    }

    public interface BasicInterface {
        int getCode();
        String getName();
    }

    public static class BasicImpl
    implements BasicInterface {
        private int code;
        private String name;
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
    }

    public static class ExtendedImpl
    extends BasicImpl {
        private OffsetDateTime creationDate;
        private OffsetDateTime updateDate;
        public OffsetDateTime getCreationDate() {
            return creationDate;
        }
        public void setCreationDate(OffsetDateTime creationDate) {
            this.creationDate = creationDate;
        }
        public OffsetDateTime getUpdateDate() {
            return updateDate;
        }
        public void setUpdateDate(OffsetDateTime updateDate) {
            this.updateDate = updateDate;
        }
    }
}
