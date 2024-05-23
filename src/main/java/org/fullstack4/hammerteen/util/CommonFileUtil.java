package org.fullstack4.hammerteen.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class CommonFileUtil {

    public List<String> fileuploads(MultipartHttpServletRequest files, String uploadFolder) {
        List<String> filenames = new ArrayList<>();
        List<MultipartFile> list = files.getFiles("file");
        if(files.getFile("file").getSize()<=0){
            return null;
        }
        for (MultipartFile file : list) {
            String fileRealName = file.getOriginalFilename();
            long size = file.getSize();
            String fileExt = fileRealName.substring(fileRealName.indexOf("."), fileRealName.length());

            UUID uuid = UUID.randomUUID();
            String[] uuids = uuid.toString().split("-");
            String newName = uuids[0] + fileRealName;

            File saveFile = new File(uploadFolder + "\\" + newName);
            try {
                file.transferTo(saveFile);
                filenames.add(newName);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filenames;

    }

    public void fileDelite(String file_directory, String file_name){
        File file = new File(file_directory+File.separator+file_name);
        file.delete();
    }

    public void fileDelite(String file_full_directory){
        File file = new File(file_full_directory);
        file.delete();
    }

    public void fileDownload(String saveDirectory, String file_name, HttpServletResponse response, HttpServletRequest request){
        String orgFileName = file_name;
        try{
            File file = new File(saveDirectory, file_name);
            InputStream is = new FileInputStream(file);
            String client = request.getHeader("User-Agent");
            if(client.indexOf("WOW64") == -1){
                orgFileName = new String(orgFileName.getBytes("UTF-8"),"ISO-8859-1");
            }
            else{
                orgFileName = new String(orgFileName.getBytes("KSC5601"),"ISO-8859-1");
            }
            //출력헤더 설정
            response.reset();
            response.setContentType("application/octect-stream");
            response.setHeader("Content-Disposition","attachment; filename=\""+orgFileName + "\"");
            response.setHeader("Content-Length",""+file.length());

            //out.clear();
            //out = pageContext.pushBody();


            OutputStream oStream = response.getOutputStream();
            byte b[] = new byte[(int)file.length()];
            int readBuffer = 0;
            while ((readBuffer = is.read(b)) > 0){
                oStream.write(b,0,readBuffer);
            }
            is.close();
            oStream.close();
        }
        catch(FileNotFoundException e){
            System.out.println("파일을 찾을 수 없습니다.");
        }

        catch(Exception e){
            System.out.println("파일 다운로드시 에러 발생");
            e.printStackTrace();
        }
    }
}
