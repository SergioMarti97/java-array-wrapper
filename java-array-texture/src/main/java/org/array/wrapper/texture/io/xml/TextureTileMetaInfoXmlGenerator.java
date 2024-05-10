package org.array.wrapper.texture.io.xml;

import org.array.wrapper.texture.Texture;
import org.array.wrapper.texture.TextureTile;
import org.array.wrapper.texture.io.xml.writer.TextureTileXmlWriter;
import org.joml.Vector2i;
import org.joml.Vector3i;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TextureTileMetaInfoXmlGenerator {

    private static final int PROGRAM_MODE_SIMPLE = 0;

    private static final int PROGRAM_MODE_DOUBLE = 1;

    private static final Vector2i tileDimensions = new Vector2i(32, 32);

    private static final double threshold = 10.0; // for image similarity

    private static TextureTile loadTextureTile(final String textureTileFile) {
        TextureTile textureTile = null;
        try {
            textureTile = new TextureTile(new File(textureTileFile), tileDimensions.x, tileDimensions.y);
            System.out.printf("Se ha cargado el mosaico de texturas (%d texturas)\n", textureTile.getGrid().getArray().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textureTile;
    }

    private static String getFileExtension(final String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    private static Path buildMetaInfoPath(final String textureTileFile) {
        Path pathTextureTileFile = Paths.get(textureTileFile);
        String textureTileFilename = pathTextureTileFile.toFile().getName();
        String extension = getFileExtension(textureTileFilename);

        String metaFileFilename = textureTileFilename.replaceAll(extension, "") + "_metainfo.xml";
        return Paths.get(pathTextureTileFile.getParent().toAbsolutePath().toString(), metaFileFilename);
    }

    private static void createFile(final Path path) {
        try {
            Files.createFile(path);
            System.out.printf("Se ha creado el archivo: %s\n", path.toFile().getName());
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.printf("Ya existe el archivo: %s\nSe va a sobrescribir\n", path.toFile().getName());
        }
    }

    private static void writeXmlFile(final Path path, final TextureTile textureTile, final String textureTileFile) {
        TextureTileXmlWriter textureTileXMLWriter = new TextureTileXmlWriter(textureTileFile);
        try {
            textureTileXMLWriter.save(textureTile, new File(path.toAbsolutePath().toString()));
            System.out.println("Se ha guardado el archivo");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void writeXmlFile(final Path path, final TextureTile textureTile, final String textureTileFile, final HashMap<Vector2i, Integer> listTexturesIds) {
        TextureTileXmlWriter textureTileXMLWriter = new TextureTileXmlWriter(textureTileFile);
        try {
            textureTileXMLWriter.save(textureTile, new File(path.toAbsolutePath().toString()), listTexturesIds);
            System.out.println("Se ha guardado el archivo");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static Vector3i obtainRGB(int hex) {
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);
        return new Vector3i(r, g, b);
    }

    private static double texturesAreEqual(Texture t1, Texture t2) {
        if (t1.getWidth() != t2.getWidth() || t1.getHeight() != t2.getHeight()) {
            return Double.MAX_VALUE;
        }

        final int size = t1.size();
        double inc = 0.0f;
        int count = 0;
        for (int i = 0; i < size; i++) {
            /*if (t1.get(i) != t2.get(i)) {
                return false;
            }*/

            // int diff = Math.abs(t1.get(i) - t2.get(i));
            Vector3i rgb1 = obtainRGB(t1.get(i));
            Vector3i rgb2 = obtainRGB(t2.get(i));
            double diff = rgb1.distance(rgb2);
            if (diff != 0) {
                count++;
                inc += diff;
            }
        }
        // return true;

        if (count != 0) {
            inc /= size;
        }

        return inc;
    }

    /**
     * @param tt1
     * @param tt2 model
     * @return
     */
    private static HashMap<Vector2i, Integer> getIndexList(final TextureTile tt1, final TextureTile tt2) {
        HashMap<Vector2i, Integer> l = new HashMap<>();
        for (int t1x = 0; t1x < tt1.getGrid().getWidth(); t1x++) {
            for (int t1y = 0; t1y < tt1.getGrid().getHeight(); t1y++) {
                int t1id = tt1.getGrid().toIndex(t1x, t1y);
                Texture t1 = tt1.getTile(t1x, t1y);

                for (int t2x = 0; t2x < tt2.getGrid().getWidth(); t2x++) {
                    for (int t2y = 0; t2y < tt2.getGrid().getHeight(); t2y++) {
                        int t2id = tt2.getGrid().toIndex(t2x, t2y);
                        Texture t2 = tt2.getTile(t2x, t2y);

                        double similarity = texturesAreEqual(t1, t2);
                        if (similarity <= threshold) {
                            l.put(new Vector2i(t1x, t1y), t2id);
                            // System.out.printf("textura modelo: %d (%dx%dy) - textura: %d (%dx%dy) | %.3f\n", t2id, t2x, t2y, t1id, t1x, t1y, similarity);
                        }
                    }
                }

            }
        }
        System.out.printf("Total texturas idénticas: %d\n", l.size());
        return l;
    }

    public static void main(String[] args) {
        int programMode = PROGRAM_MODE_SIMPLE;
        if (args.length != 0) {

            String textureTileFile = args[0];

            if (args.length == 3) {
                tileDimensions.x = Integer.parseInt(args[1]);
                tileDimensions.y = Integer.parseInt(args[2]);
            }

            String textureTileFile2 = "";
            if (args.length == 2) {
                programMode = PROGRAM_MODE_DOUBLE;
                textureTileFile2 = args[1];
            }
            if (args.length == 4) {
                programMode = PROGRAM_MODE_DOUBLE;
                textureTileFile2 = args[3];
            }

            System.out.println("Programa modo: " + programMode);

            TextureTile textureTile = loadTextureTile(textureTileFile);

            if (textureTile != null) {

                Path pathMetaInfoFile;
                switch (programMode) {
                    case PROGRAM_MODE_SIMPLE:
                        pathMetaInfoFile = buildMetaInfoPath(textureTileFile);
                        createFile(pathMetaInfoFile);
                        writeXmlFile(pathMetaInfoFile, textureTile, textureTileFile);
                        break;
                    case PROGRAM_MODE_DOUBLE:
                        TextureTile textureTile2 = loadTextureTile(textureTileFile2);
                        if (textureTile2 != null) {
                            pathMetaInfoFile = buildMetaInfoPath(textureTileFile2);
                            createFile(pathMetaInfoFile);

                            HashMap<Vector2i, Integer> listTexturesIds = getIndexList(textureTile2, textureTile);
                            writeXmlFile(pathMetaInfoFile, textureTile2, textureTileFile2, listTexturesIds);
                        } else {
                            System.out.println("No se ha podido cargar el segundo mosaico de texturas");
                        }
                        break;
                }

            } else {
                System.out.println("No se ha podido cargar el mosaico de texturas");
            }
        } else {
            System.out.println("No hay argumentos");
        }
    }

}
