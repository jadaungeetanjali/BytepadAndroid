package in.silive.bo;

/**
 * Created by AKG002 on 03-08-2016.
 */
public class Config {
    static final String KEY_SHAREDPREF_NAME = "bytepad";
    static final String PAPERS_LOADED = "papersLoaded";
    static final String Subject_Loaded="subjectsLoaded";
    static final int DB_VERSION = 3;
    static final String DB_NAME = "bytepad";


    public static final String BASE_URL = "http://testapi.silive.in/api";


    static final String KEY_GCM_SENT_TO_SEVER = "sent_to_server";
    public static final String KEY_DOWNLOADED_LIST = "downloadedList";
    public static final String TIMESTAMP ="time";
    public static String KEY_BYTEPAD = "bytepad";
    public static String KEY_DOWNLOAD_DIR = "bytepadDownload";
    public static final String TOPIC_GLOBAL = "global";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String RESPONSE_CODE = "Response_Code";
    public static final String SHARED_PREFS = "bytepad_prefs";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String FIREBASE_ID_SENT = "FirebaseIdSendToServer";
    public static final String FIREBASE_TOKEN_UPDATE = "/send_gcm_notifcation_";
    public static String fcm_token = "fcm_token";

}

