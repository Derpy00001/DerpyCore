package me.derpy.core.Logger;

import com.sun.istack.internal.NotNull;
import org.bukkit.plugin.Plugin;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nullable;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogHandler extends Logger {
    private String name;

    public LogHandler(@NotNull Plugin plugin){
        super(plugin.getClass().getCanonicalName(), null);
        name = LogColor.LIGHT_PURPLE+"["+plugin.getName()+"]: "+LogColor.RESET;
        setParent(plugin.getServer().getLogger());
        plugin.getServer().getLogger().
        setLevel(Level.ALL);
    }

    /**
     * Log a message to the server console
     * @param logRecord The log record to send to console
     */
    @Override
    public void log(@NotNull LogRecord logRecord){
        logRecord.setMessage(this.name+logRecord.getMessage());
        super.log(logRecord);
    }

    /**
     * Log a message to the server console
     * @param message The message to log in the console
     */
    public void log(@NotNull String message){
        this.log(new LogRecord(Level.INFO, message));
    }

    /**
     * Log a message to the server console with color
     * @param message The message to log in the console
     * @param color The color to display the message as
     */
    public void log(@NotNull String message, @NotNull LogColor color){this.log(new LogRecord(Level.INFO, color+message+LogColor.RESET));}

    /**
     * Log a large ascii text to the server console to flex
     * @param message The message to log in the console
     * @param color The color to display the message as
     */
    public void logLargeText(@NotNull String message, @Nullable LogColor color){
        message = message.toLowerCase();
        String[] parse = new String[8];
        for(int i =0;i<message.length();i++){
            if(!Ascii.ALPHABET.contains(String.valueOf(message.charAt(i)))){continue;}
            String[] split = Ascii.TEXT_LIST[Ascii.ALPHABET.indexOf(message.charAt(i))].split("\\n");
            for(int x = 0;x<split.length;x++){
                if(parse[x]==null){parse[x]=color!=null?color.toString():"";}
                parse[x]=parse[x]+split[x];
            }
        }
        String logMessage = "\n\n";
        for (String s : parse) {
            logMessage = logMessage.concat(s).concat("\n");
        }
        this.log(new LogRecord(Level.INFO, logMessage+LogColor.RESET));
    }

    /**
     * The available Ansi Color Codes
     */
    public enum LogColor{
        BLACK(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString()),
        DARK_BLUE(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString()),
        DARK_GREEN(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString()),
        DARK_AQUA(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString()),
        DARK_RED(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString()),
        DARK_PURPLE(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString()),
        GOLD(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString()),
        GRAY(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString()),
        BLUE(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString()),
        DARK_GRAY(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString()),
        GREEN(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString()),
        AQUA(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString()),
        RED(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString()),
        LIGHT_PURPLE(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString()),
        YELLOW(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString()),
        WHITE(Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString()),
        MAGIC(Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString()),
        BOLD(Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString()),
        STRIKETHROUGH(Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString()),
        UNDERLINE(Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString()),
        ITALIC(Ansi.ansi().a(Ansi.Attribute.ITALIC).toString()),
        RESET(Ansi.ansi().a(Ansi.Attribute.RESET).toString());
        LogColor(String string){
            this.code = string;
        }
        private String code;
        public String toString(){
            return this.code;
        }
    }
}
