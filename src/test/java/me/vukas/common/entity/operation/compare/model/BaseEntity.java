package me.vukas.common.entity.operation.compare.model;

public abstract class BaseEntity implements Base {
    private int commonInt;
    private int baseEntityInt;
    protected int baseEntityProtectedInt;
    public int baseEntityPublicInt;

    private String commonString;
    private String baseEntityString;
    protected String baseEntityProtectedString;
    public String baseEntityPublicString;

    private static int commonStaticInt;
    private static int baseEntityStaticInt;
    protected static int baseEntityProtectedStaticInt;
    public static int baseEntityPublicStaticInt;

    private static String commonStaticString;
    private static String baseEntityStaticString;
    protected static String baseEntityProtectedStaticString;
    public static String baseEntityPublicStaticString;
}
