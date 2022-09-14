package vn.ltt.shop.model;

public enum Role {
    ADMIN("ADMIN"), USER("USER");

    private String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Role parseRole(String value) {
        Role[] values = values();
        for (Role type : values) {
            if (type.value.equals(value))
                return type;
        }
        throw new IllegalArgumentException("invalid");
    }
}
