package com.kazakovproduction.gogot.model.settings;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class FileReaderWriter {

    public static void writeToFile(Context context, String fileName, int[] data) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput
                    (fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            new File(context.getFilesDir(), fileName);
        }
        try {
            fos = context.openFileOutput
                    (fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fos != null;
        try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            for (int i : data) {
                osw.append(String.valueOf(i)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] readPlacesFile(Context context, String fileName, int[] def) {
        int[] result = Arrays.copyOf(def, def.length);                          // TODO add flexibility
        try (InputStream inputStream = context.openFileInput(fileName)) {
            int i = 0;
            if (inputStream != null) {
                InputStreamReader inputStreamReader =
                        new InputStreamReader(inputStream);
                BufferedReader bufferedReader =
                        new BufferedReader(inputStreamReader);
                String receiveString;

                while ((receiveString = bufferedReader.readLine()) != null) {
                    result[i] = Integer.parseInt(receiveString);
                    ++i;
                }
            }
        } catch (FileNotFoundException e) {
            writeToFile(context, fileName, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
