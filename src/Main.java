import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String path = "D:/Games/savegames/saves.zip";
        String pathToUnpack = "D:/Games/savegames";
        openZip(path, pathToUnpack);
        System.out.println(openProgress(pathToUnpack).toString());
    }

    public static void openZip(String path, String pathToUnpack) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null){
                String name = new File(entry.getName()).getName();
                FileOutputStream fos = new FileOutputStream(pathToUnpack.concat("/" + name));
                int c;
                while ((c = zis.read()) != -1){
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress(String path){
        GameProgress gp = null;
        try(FileInputStream fis = new FileInputStream(path.concat("/save1.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis)){
            gp = (GameProgress) ois.readObject();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return gp;
    }
}