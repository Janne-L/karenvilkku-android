package fi.thl.koronahaavi.common

import android.content.Context
import android.content.SharedPreferences
import java.util.*

private const val PREF_KEY_LANGUAGE = "language"

fun Context.withSavedLanguage(): Context {
    return withLanguage(getSavedLanguage())
}

fun Context.withLanguage(language: String?): Context {

    if (language == null || Locale.getDefault().language == language) {
        return this
    }

    val locale = Locale(language)
    Locale.setDefault(locale)

    val cfg = android.content.res.Configuration()
    cfg.updateFrom(resources.configuration)
    cfg.setLocale(locale)

    return createConfigurationContext(cfg)
}

fun Context.getSavedLanguage(): String? {
    return languagePreferences().getString(PREF_KEY_LANGUAGE, null)
}

fun Context.setSavedLanguage(language: String?) {
    languagePreferences().edit()
        .putString(PREF_KEY_LANGUAGE, language)
        .apply()
}

private fun Context.languagePreferences(): SharedPreferences {
    // No encryption is needed for language preference, and not sure if SettingsRepository could
    // be accessed early enough (attachBaseContext) => use a separate SharedPreferences.
    return getSharedPreferences("language", Context.MODE_PRIVATE)
}
