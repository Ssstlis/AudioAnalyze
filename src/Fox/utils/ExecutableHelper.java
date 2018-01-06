package Fox.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExecutableHelper {

    public static List<String> FilesToStrings(@NotNull List<File> FileList) {
        List<String> Temp = new ArrayList<>();
        for (File file : FileList)
            Temp.add(file.getPath());
        return Temp;
    }

    public static List<File> GetFileList(
            @NotNull String pathname,
            @NotNull FileFilter filter) {
        List<File> files = new ArrayList<>();
        File check = new File(pathname);

        if (!check.exists())
            return files;

        File[] path = new File(pathname).listFiles(filter);
        File[] alls = new File(pathname).listFiles();

        if (path != null)
            files.addAll(Arrays.asList(path));

        if (alls != null)
            for (File file : alls)
                if (file.isDirectory())
                    files.addAll(GetFileList(file.getPath(), filter));

        return files;
    }

    @NotNull
    public static File SearchFile(
            @NotNull String location,
            @NotNull final String name) {

        return ExecutableHelper.GetFileList(
                location,
                new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.exists()
                                && pathname.getName().endsWith(name);
                    }
                }).get(0);
    }

    @NotNull
    public static List<File> SearchAllFile(
            @NotNull String location,
            @NotNull final String name) {

        return ExecutableHelper.GetFileList(
                location,
                new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.exists()
                                && pathname.getName().endsWith(name);
                    }
                });
    }
}
