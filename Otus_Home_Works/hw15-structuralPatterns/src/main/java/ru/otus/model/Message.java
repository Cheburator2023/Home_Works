package ru.otus.model;

@SuppressWarnings({"java:S107", "java:S1135"})
public class Message implements Cloneable {
    private final long id;
    private final String field1;
    private final String field2;
    private final String field3;
    private final String field4;
    private final String field5;
    private final String field6;
    private final String field7;
    private final String field8;
    private final String field9;
    private final String field10;
    private final String field11;
    private final String field12;
    private ObjectForMessage field13;

    // todo: 1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)

    private Message(
            long id,
            String field1,
            String field2,
            String field3,
            String field4,
            String field5,
            String field6,
            String field7,
            String field8,
            String field9,
            String field10,
            String field11,
            String field12,
            ObjectForMessage field13) {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
        this.field9 = field9;
        this.field10 = field10;
        this.field11 = field11;
        this.field12 = field12;
        this.field13 = field13;
    }

    public long getId() {
        return id;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    public String getField4() {
        return field4;
    }

    public String getField5() {
        return field5;
    }

    public String getField6() {
        return field6;
    }

    public String getField7() {
        return field7;
    }

    public String getField8() {
        return field8;
    }

    public String getField9() {
        return field9;
    }

    public String getField10() {
        return field10;
    }

    public String getField11() {
        return field11;
    }

    public String getField12() {
        return field12;
    }

    public ObjectForMessage getField13() {
        return field13;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message)) return false;

        if (getId() != message.getId()) return false;
        if (getField1() != null ? !getField1().equals(message.getField1()) : message.getField1() != null) return false;
        if (getField2() != null ? !getField2().equals(message.getField2()) : message.getField2() != null) return false;
        if (getField3() != null ? !getField3().equals(message.getField3()) : message.getField3() != null) return false;
        if (getField4() != null ? !getField4().equals(message.getField4()) : message.getField4() != null) return false;
        if (getField5() != null ? !getField5().equals(message.getField5()) : message.getField5() != null) return false;
        if (getField6() != null ? !getField6().equals(message.getField6()) : message.getField6() != null) return false;
        if (getField7() != null ? !getField7().equals(message.getField7()) : message.getField7() != null) return false;
        if (getField8() != null ? !getField8().equals(message.getField8()) : message.getField8() != null) return false;
        if (getField9() != null ? !getField9().equals(message.getField9()) : message.getField9() != null) return false;
        if (getField10() != null ? !getField10().equals(message.getField10()) : message.getField10() != null)
            return false;
        if (getField11() != null ? !getField11().equals(message.getField11()) : message.getField11() != null)
            return false;
        if (getField12() != null ? !getField12().equals(message.getField12()) : message.getField12() != null)
            return false;
        return getField13() != null ? getField13().equals(message.getField13()) : message.getField13() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getField1() != null ? getField1().hashCode() : 0);
        result = 31 * result + (getField2() != null ? getField2().hashCode() : 0);
        result = 31 * result + (getField3() != null ? getField3().hashCode() : 0);
        result = 31 * result + (getField4() != null ? getField4().hashCode() : 0);
        result = 31 * result + (getField5() != null ? getField5().hashCode() : 0);
        result = 31 * result + (getField6() != null ? getField6().hashCode() : 0);
        result = 31 * result + (getField7() != null ? getField7().hashCode() : 0);
        result = 31 * result + (getField8() != null ? getField8().hashCode() : 0);
        result = 31 * result + (getField9() != null ? getField9().hashCode() : 0);
        result = 31 * result + (getField10() != null ? getField10().hashCode() : 0);
        result = 31 * result + (getField11() != null ? getField11().hashCode() : 0);
        result = 31 * result + (getField12() != null ? getField12().hashCode() : 0);
        result = 31 * result + (getField13() != null ? getField13().hashCode() : 0);
        return result;
    }

    public Builder toBuilder() {
        return new Builder(id, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4='" + field4 + '\'' +
                ", field5='" + field5 + '\'' +
                ", field6='" + field6 + '\'' +
                ", field7='" + field7 + '\'' +
                ", field8='" + field8 + '\'' +
                ", field9='" + field9 + '\'' +
                ", field10='" + field10 + '\'' +
                ", field11='" + field11 + '\'' +
                ", field12='" + field12 + '\'' +
                ", field13=" + field13 +
                '}';
    }

    @Override
    public Message clone() {
        try {
            Message clonedMessage = (Message) super.clone();
            clonedMessage.field13 = this.field13.clone(); // Clone the ObjectForMessage field
            return clonedMessage;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Builder {
        private final long id;
        private String field1;
        private String field2;
        private String field3;
        private String field4;
        private String field5;
        private String field6;
        private String field7;
        private String field8;
        private String field9;
        private String field10;
        private String field11;
        private String field12;
        private ObjectForMessage field13;

        public Builder(long id) {
            this.id = id;
        }

        private Builder(long id, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, ObjectForMessage field13) {
            this.id = id;
        }

        public Builder field1(String field1) {
            this.field1 = field1;
            return this;
        }

        public Builder field2(String field2) {
            this.field2 = field2;
            return this;
        }

        public Builder field3(String field3) {
            this.field3 = field3;
            return this;
        }

        public Builder field4(String field4) {
            this.field4 = field4;
            return this;
        }

        public Builder field5(String field5) {
            this.field5 = field5;
            return this;
        }

        public Builder field6(String field6) {
            this.field6 = field6;
            return this;
        }

        public Builder field7(String field7) {
            this.field7 = field7;
            return this;
        }

        public Builder field8(String field8) {
            this.field8 = field8;
            return this;
        }

        public Builder field9(String field9) {
            this.field9 = field9;
            return this;
        }

        public Builder field10(String field10) {
            this.field10 = field10;
            return this;
        }

        public Builder field11(String field11) {
            this.field11 = field11;
            return this;
        }

        public Builder field12(String field12) {
            this.field12 = field12;
            return this;
        }

        public Builder field13(ObjectForMessage field13) {
            this.field13 = field13;
            return this;
        }

        public Message build() {
            return new Message(id, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
        }
    }
}
