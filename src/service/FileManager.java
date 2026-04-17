package service;

import java.io.*;
import java.util.HashMap;
import model.ResultRecord;

public class FileManager {

    private static final int MAX_SLOTS = 7;

    // ===== SAVE =====
    public void saveResult(String username, int slot, ResultRecord record) {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdir();

            File file = new File("data/" + username + ".txt");

            String[] slots = new String[MAX_SLOTS];

            // โหลดของเดิมก่อน
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                int i = 0;
                while ((line = br.readLine()) != null && i < MAX_SLOTS) {
                    slots[i++] = line;
                }
                br.close();
            }

            // overwrite slot
            slots[slot - 1] = record.toFileString();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < MAX_SLOTS; i++) {
                if (slots[i] != null) {
                    bw.write(slots[i]);
                }
                bw.newLine();
            }

            bw.close();
            System.out.println("Saved to slot " + slot);

        } catch (IOException e) {
            System.out.println("Error saving result.");
        }
    }

    // ===== LOAD =====
    public HashMap<Integer, ResultRecord> loadResults(String username) {
        HashMap<Integer, ResultRecord> map = new HashMap<>();

        try {
            File file = new File("data/" + username + ".txt");
            if (!file.exists()) return map;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int index = 1;

            while ((line = br.readLine()) != null && index <= MAX_SLOTS) {
                if (!line.trim().isEmpty()) {
                    map.put(index, ResultRecord.fromString(line));
                }
                index++;
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }

        return map;
    }
}