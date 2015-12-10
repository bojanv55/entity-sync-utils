package me.vukas.common.entity.operation.compare.model;

public class ChildEntity extends BaseEntity {
    protected int baseEntityProtectedInt;
    public int baseEntityPublicInt;

    protected String baseEntityProtectedString;
    public String baseEntityPublicString;

    private int commonInt;
    private int childEntityInt;
    protected int childEntityProtectedInt;
    public int childEntityPublicInt;

    private String commonString;
    private String childEntityString;
    protected String childEntityProtectedString;
    public String childEntityPublicString;

    protected static int baseEntityProtectedStaticInt;
    public static int baseEntityPublicStaticInt;

    protected static String baseEntityProtectedStaticString;
    public static String baseEntityPublicStaticString;

    private static int commonStaticInt;
    private static int childEntityStaticInt;
    protected static int childEntityProtectedStaticInt;
    public static int childEntityPublicStaticInt;

    private static String commonStaticString;
    private static String childEntityStaticString;
    protected static String childEntityProtectedStaticString;
    public static String childEntityPublicStaticString;

    private ChildEntity parent;

    @Override
    public void base() {
    }
}
