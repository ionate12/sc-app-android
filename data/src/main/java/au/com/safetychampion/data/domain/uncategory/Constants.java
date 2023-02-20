package au.com.safetychampion.data.domain.uncategory;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import au.com.safetychampion.data.BuildConfig;

public final class Constants {
    // TODO: 09/12/2022 BuildConfig?
    public static final String AUTHORITY = "au.com.safetychampion.scmobile.fileprovider";
    //    public static String API_ROOT_URL = "https://api.dev.safetychampion.tech";
//    public static String API_ROOT_URL = BuildConfig.ROOT_API;
    public static String DEFAULT_API_ROOT_URL = "https://api.safetychampion.online";
//    public static final String FRONTEND_URL = BuildConfig.ROOT_FRONTEND;
    public static String VERSION_DEBUG = "3.25.0";
//    public static String[] FETCH_MODULES = BuildConfig.FETCH_MODULES;
    public static final String APPLICATION_JSON = "application/json";
    public static String TOKEN = null;
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String NULL_USER = "NULL_USER";
    public static String SPINNER_HEAD = "--Please Select--";
    public static final String MESSAGE = "MESSAGE";
    public static final String tz = TimeZone.getDefault().getID();
    public static final String GHSCODE_ID = "3212a37a-9c32-430e-af92-75c1d73c8303";

    public static final String RICHTEXT_CSS = "<style>.ql-align-right {text-align: right;}.ql-align-center {text-align: center;}.ql-align-justify {text-align: justify;}.ql-size-huge {font-size: 2.5em;}.ql-size-large {font-size: 1.5em;}.ql-size-small {font-size: 0.75em;}</style>";

    public enum DATE_FORMAT {
        MM_DD_YYYY, YYYY_MM_DD, HH_MM, DD_MM_YYYY_HH_MM;

        @NotNull
        public String toString() {
            switch (this) {
                case HH_MM:
                    return "hh:mm";
                case MM_DD_YYYY:
                    return "mm-dd-yyyy";
                case DD_MM_YYYY_HH_MM:
                    return "dd-mm-yyyy hh:mm";
                default: return "yyyy-mm-dd";
            }
        }
    }

    public static final String ERROR_LAYOUT_TAG = "ERR_MSG";


    public static final List<String> ROLE_SUGGESTION_LIST = Arrays.asList(
            "Employee", "Employees", "worker", "workers", "Team Member", "Team Members", "Client", "Clients",
            "Employee | Driver", "Employee | Office", "Employee | Packer", "Employee / Team Member", "Staff",
            "Staff Member", "Employee", "Employee", "Worker", "Workers", "Team Member", "Team Members", "Employee / Worker",
            "Employee (Office)", "Employee (Production yard)", "Full time Contractor - Downer", "Full time Contractor - HQS",
            "Full time Contractor - Maxwell", "Full time Contractor – Toll", "Truck Driver", "Management", "Support Worker",
            "Client", "Clients", "Labour Hire", "Employee: Matthews", "Teacher", "Teachers", "Worker - Admin"
    );


    public static final long MD_MOTION_DURATION = 500;

    public static final String AUTHORIZATION = "Authorization";
    public static final String Content = "Content-Type";
    public static final String ContentTypeHeader = "Content-Type: application/json";





}
