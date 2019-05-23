package app.utils.queries;

public final class CarQueries {

    public static final String FROM_CAR = " FROM Car " ;
    public static final String CAR_WHERE_REGISTRATION_NUMBER = "FROM Car WHERE registration_number = " ;

    public static final String AND_IS_AVAILABLE = " AND is_available = 1";
    public static final String REG_NUM_PARAM = ":regNumber";
    public static final String REG_NUM_ACTUAL = "regNumber";
}
