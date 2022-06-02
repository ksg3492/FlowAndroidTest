#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_sunggil_flowandroidtest_data_Name_id(
        JNIEnv *env,
        jobject /* this */) {
    std::string id = "w7sXm4XQVv1C8FXRPnyj";
    return env->NewStringUTF(id.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_sunggil_flowandroidtest_data_Name_key(
        JNIEnv *env,
        jobject /* this */) {
    std::string key = "rw_vpktaUd";
    return env->NewStringUTF(key.c_str());
}