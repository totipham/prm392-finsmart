package com.example.finsmart;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class FinSmartApplication extends Application {
    Map<String, Object> config;

    @Override
    public void onCreate() {
        super.onCreate();
        configCloudinary();
    }

    void configCloudinary() {
        config = new HashMap<>();
        config.put("cloud_name", "ddr0pf043");
        config.put("api_key", "814571977924379");
        config.put("api_secret", "p7WjgkOnh46EF1p9iN-Aa-6X0vY");
        config.put("secure", true);
        MediaManager.init(this, config);
    }

}
