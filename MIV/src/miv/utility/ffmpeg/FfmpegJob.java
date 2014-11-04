package miv.utility.ffmpeg;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;


public class FfmpegJob {

        public String mFfmpegPath;
        public String inputPath;
        public String outputPath;
        public int startPoint = -1;
        public int processTime = -1;
        
        
        public FfmpegJob(String ffmpegPath) {
                mFfmpegPath = ffmpegPath;
        }
        
        public ProcessRunnable create() {
                    
                
                final List<String> cmd = new LinkedList<String>();
                
                cmd.add(mFfmpegPath);
                
                cmd.add(FFMPEGArg.FILE_INPUT);
                cmd.add(inputPath);
                cmd.add(FFMPEGArg.NO_VIDEO);
                cmd.add(FFMPEGArg.AUDIO_TRACK);
                cmd.add(FFMPEGArg.COPY);
              
                if(startPoint >= 0 && processTime >= 0){
                	cmd.add(FFMPEGArg.START_CUT);
                	cmd.add(String.valueOf(startPoint));
                	cmd.add(FFMPEGArg.FINISH_CUT);
                	cmd.add(String.valueOf(processTime));
                }

                cmd.add(outputPath);
                

                Log.d(this.toString(), cmd.toString());
                final ProcessBuilder pb = new ProcessBuilder(cmd);
                return new ProcessRunnable(pb);
        }
        
        public class FFMPEGArg {
                String key;
                String value;
                
                public static final String ROOT = "su";
                public static final String COMMAND = "-c";
                public static final String START_CUT = "-ss";
                public static final String FINISH_CUT = "-t";
                public static final String FILE_INPUT = "-i";
                public static final String NO_VIDEO = "-vn";
                public static final String AUDIO_TRACK = "-acodec";
                public static final String COPY = "copy";
        }
}