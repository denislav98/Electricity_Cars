package app.utils.queries;

public final class ClientQuieries {

    public static final String CLIENT_WITH_DRIVING_LICENSE = "From Client WHERE driving_license_number = ";
    public static final String FROM_CLIENT = "From Client";
    public static final String CLIENT_WITH_USERNAME = "From Client WHERE username = ";
    public static final String AND_LAST_NAME = " and last_name = " ;
    public static final String FROM_CLIENT_WITH_USERNAME = "From Client WHERE username = ";

    public static final String  LICENSE_NUMBER_PARAM = " :license";
    public static final String LICENSE_NUMBER_ACTUAL = "license";
    public static final String CLIENT_ID_PARAM = ":id";
    public static final String CLIENT_ID_ACTUAL = "id";
    public static final String USERNAME_PARAM = ":username";
    public static final String USERNAME_ACTUAL = "username";
}
