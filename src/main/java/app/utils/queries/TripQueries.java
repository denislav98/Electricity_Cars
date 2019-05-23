package app.utils.queries;

public final class TripQueries {

    public static final double TAX_CONSTANT = 0.30;
    public static final String FROM_TRIP = "FROM Trip";
    public static final String FROM_TRIP_WITH_CLIENT_ID = "FROM Trip WHERE client_id = ";

    public static final String ID_PARAM = ":id";
    public static final String ID_ACTUAL = "id";
    public static final String FROM_TRIP_WITH_END_TIME = "FROM Trip WHERE end_trip_time = NULL AND client_id = ";
}
