package ServerLogic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Serialization {



    /* Throws exception if deserialization did not work to alarm recipient.
     * @return Object (deserialized Object, has to be casted to appropriate type and tested for class type)
     */

    public static Object deserialize(Path filePath) throws ClassNotFoundException, IOException {


        try (InputStream is = Files.newInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(is)) {

            Object deserializedObject = ois.readObject();
            File file = new File(String.valueOf(filePath));
            file.delete();
            return deserializedObject;

        }

    }

    public static void serialize(Serializable toSer, Path filePath) {
        try (
                OutputStream os = Files.newOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(toSer);

        } catch (FileNotFoundException e) {
            System.out.printf("Could not save file " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.printf("ServerLogic.Serialization does not work");
            e.printStackTrace();
        }

    }
}
