package fr.romain120105.launcher.starter;

public class Ram
{
    private short ram;
    private String unit;
    
    public Ram(final short ram, final String unit) {
        this.ram = ram;
        this.unit = unit;
    }
    
    public Ram(final short  ram) {
        this(ram, "M");
    }
    
    public String getRamArguments() {
        final StringBuilder builder = new StringBuilder();
        builder.append("-Xmx" + this.ram + this.unit);
        return builder.toString();
    }
}
