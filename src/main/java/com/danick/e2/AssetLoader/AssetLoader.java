package com.danick.e2.AssetLoader;

import com.danick.e2.renderer.Graphic;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jdk.jshell.spi.ExecutionControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.io.ObjectInputStream;
public class AssetLoader{
    static String assetFilePath = "Assets.json";
    static AssetList assetList= new AssetList();
    static HashMap<String, Object> loadedFiles = new HashMap<>();

    public static void Init() throws FileNotFoundException {
        File assetFile = new File(assetFilePath);

        InputStream inputStream = AssetLoader.class.getClassLoader().getResourceAsStream(assetFilePath);

        if(inputStream == null)
            throw new FileNotFoundException();

        String data = "";
        try{
            Scanner myReader = new Scanner(inputStream);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();

            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        parseJson(data);

        loadInitFiles();
    }


    public static Object getAsset(String key) {
        if(loadedFiles.containsKey(key))
            return loadedFiles.get(key);

        return LoadAsset(key);
    }

    public static BufferedImage getImageAsset(String key) {
        if(loadedFiles.containsKey(key))
            return (BufferedImage) loadedFiles.get(key);

        return (BufferedImage)LoadAsset(key);
    }
    public static Graphic getGraphicAsset(String key)  {
        BufferedImage image = getImageAsset(key);

        return Graphic.fromImage(image);
    }

    private static void createAssetFile(File assetFile){
        try{
            assetFile.createNewFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void parseJson(String data){
        Gson gson = new Gson();
        try{
            assetList = gson.fromJson(data,AssetList.class);
        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }

        if(assetList == null)
            throw new FormatFlagsConversionMismatchException("File empty or wrong format",'a');
    }

    private static void loadInitFiles()  {
        List<AssetItem> supList = assetList.textures.stream().filter(n -> n.init == true).collect(Collectors.toList());

        if(supList == null)
            return;

        for (var item : supList) {
            LoadAsset(item);
        }
    }

    private static Object LoadAsset(String key){
        if(loadedFiles.containsKey(key))
            return loadedFiles.get(key);

        Optional<AssetItem> item = assetList.textures.stream().filter(n -> n.key.equals(key)).findFirst();

        if(!item.isPresent())
            return null;
        return LoadAsset(item.get());
    }

    private static Object LoadAsset(AssetItem item)  {
        if(loadedFiles.containsKey(item.key))
            return loadedFiles.get(item.key);

        InputStream inputStream = AssetLoader.class.getResourceAsStream("/"+ item.path+item.filename);

        if(inputStream == null)
            return null;

        try {
            if(item.type.equals("Image"))
                loadedFiles.put(item.key, ImageIO.read(inputStream));
            else if(item.type.equals("Object"))
                loadedFiles.put(item.key, new ObjectInputStream(inputStream));
            else
                throw new ExecutionControl.NotImplementedException(item.type + " Type is not implemented" );
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return loadedFiles.get(item.key);
    }
}
