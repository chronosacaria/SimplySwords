package net.sweenus.simplyswords.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SimplySwordsConfig {
    private static final HashMap<String, Boolean> SWORDS = new HashMap<>();
    private static final HashMap<String, Integer> INT_OPTIONS = new HashMap<>();
    private static final HashMap<String, Float> FLOAT_OPTIONS = new HashMap<>();

    public static boolean getBooleanValue(String key) {
        if (!SWORDS.containsKey(key)) {
            System.out.println(key);
        }
        return SWORDS.getOrDefault(key, null);
    }

    public static int getIntValue(String key) {
        if (!INT_OPTIONS.containsKey(key)) {
            System.out.println(key);
        }
        return INT_OPTIONS.getOrDefault(key, null);
    }

    public static float getFloatValue(String key) {
        if (!FLOAT_OPTIONS.containsKey(key)) {
            System.out.println(key);
        }
        return FLOAT_OPTIONS.getOrDefault(key, null);
    }

    public static void init() {
        SWORDS.put("add_weapons_to_loot_tables", true);
        SWORDS.put("electrite_sword", true);
        SWORDS.put("runic_sword", true);

        INT_OPTIONS.put("speed_chance", 15);
        INT_OPTIONS.put("slowness_chance", 50);
        INT_OPTIONS.put("toxin_chance", 15);
        INT_OPTIONS.put("freeze_chance", 15);
        INT_OPTIONS.put("brimstone_chance", 15);
        INT_OPTIONS.put("wildfire_chance", 20);
        INT_OPTIONS.put("watcher_chance", 15);
        INT_OPTIONS.put("omen_chance", 25);
        INT_OPTIONS.put("lightning_chance", 10);
        INT_OPTIONS.put("levitation_chance", 15);
        INT_OPTIONS.put("omen_instantkill_threshold", 60);
        INT_OPTIONS.put("freeze_duration", 120);
        INT_OPTIONS.put("levitation_duration", 50);
        INT_OPTIONS.put("speed_duration", 300);
        INT_OPTIONS.put("slowness_duration", 50);
        INT_OPTIONS.put("toxin_duration", 150);


        FLOAT_OPTIONS.put("loot_table_weight", 0.3f);
        FLOAT_OPTIONS.put("omen_absorption_amount", 1f);
        FLOAT_OPTIONS.put("watcher_restore_amount", 1f);
    }

    public static void loadConfig() {
        JsonObject json;
        json = Config.getJsonObject(Config.readFile(new File("config/simplyswords/basic_config.json5")));
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            SWORDS.put(entry.getKey(), entry.getValue().getAsBoolean());
        }

        json = Config.getJsonObject(Config.readFile(new File("config/simplyswords/value_config.json5")));
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            INT_OPTIONS.put(entry.getKey(), entry.getValue().getAsInt());
        }

        json = Config.getJsonObject(Config.readFile(new File("config/simplyswords/misc_config.json5")));
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            FLOAT_OPTIONS.put(entry.getKey(), entry.getValue().getAsFloat());
        }
    }


    public static void generateConfigs(boolean overwrite) {
        StringBuilder config = new StringBuilder("{\n");
        int i = 0;
        for (String key : SWORDS.keySet()) {
            config.append("  \"").append(key).append("\": ").append(SWORDS.get(key));
            ++i;
            if (i < SWORDS.size()) {
                config.append(",");
            }
            config.append("\n");
        }
        config.append("}");
        Config.createFile("config/simplyswords/basic_config.json5", config.toString(), overwrite);

        config = new StringBuilder("{\n");
        i = 0;
        for (String item : INT_OPTIONS.keySet()) {
            config.append("  \"").append(item).append("\": ").append(INT_OPTIONS.get(item));
            ++i;
            if (i < INT_OPTIONS.size()) {
                config.append(",");
            }
            config.append("\n");
        }
        config.append("}");
        Config.createFile("config/simplyswords/value_config.json5", config.toString(), overwrite);

        config = new StringBuilder("{\n");
        i = 0;
        for (String item : FLOAT_OPTIONS.keySet()) {
            config.append("  \"").append(item).append("\": ").append(FLOAT_OPTIONS.get(item));
            ++i;
            if (i < FLOAT_OPTIONS.size()) {
                config.append(",");
            }
            config.append("\n");
        }
        config.append("}");
        Config.createFile("config/simplyswords/misc_config.json5", config.toString(), overwrite);

    }
}