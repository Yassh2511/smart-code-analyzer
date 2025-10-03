package com.smartanalyzer.cli;

public class CommandLineInterface
{
    private String sourceDirectory;
    private String outputFormat="console";
    private String outputFile;
    private boolean verbose=false;
    private boolean showHelp=false;

    public boolean parseArguments(String[] args)
    {
        if(args.length==0)
        {
            sourceDirectory=".";
            return true;
        }

        for(int i=0;i<args.length;i++)
        {
            String arg=args[i];
            switch(arg)
            {
                case "-h":
                case "-help":
                    showHelp=true;
                    return false;

                case "-s:":
                case "--source":
                    if(i+1<args.length)
                    {
                        sourceDirectory=args[++i];
                    }
                    else
                    {
                        System.err.println("Erro:-- source requires a directory path");
                        return false;
                    }
                    break;

                case "-f":
                case "--format":
                    if(i+1<args.length)
                    {
                        outputFormat=args[++i];
                        if(!isValidFormat(outputFormat))
                        {
                            System.err.println("Error:Invalid format.use:console,html,json");
                            return false;
                        }
                    }
                    else
                    {
                        System.err.println("Error-- Format reuires a  value");
                    }
                    break;

                case "-o":
                case "--output":
                    if(i+1<args.length)
                    {
                        outputFile=args[++i];
                    }
                    else
                    {
                        System.err.println("Error:otuput requires a file path");
                        return false;
                    }

                    break;

                case "-v":
                case"--verbose":
                     verbose=true;
                     break;

                default:
                    if(sourceDirectory==null && !arg.startsWith("-"))
                    {
                        sourceDirectory=arg;
                    }
                    else {
                        System.err.println("Error:unknown option:"+arg);
                        return false;
                    }
            }
        }
        if(sourceDirectory==null)
        {
            sourceDirectory=".";
        }
        return true;
    }

    private boolean isValidFormat(String outputFormat)
    {
        return "console".equals(outputFormat)||"html".equals(outputFormat)||"json".equals(outputFormat);
    }

    public void showHelp()
    {
        System.out.println("Smart code Analyzer-usage:");
        System.out.println();
        System.out.println("java -jar smart-code-analyzer.jar [OPTIONS] <directory>");
        System.out.println();
        System.out.println("Options:");
        System.out.println(" -s,  --source <dir>   Source directory to analyze (default:current directort)");
        System.out.println(" -f,  --format<format> Output format:console,html,json(default:console");
        System.out.println(" -o,  --output <file>  Output file path(optional)");
        System.out.println(" -v,  --verbose        Enable verbose output");
        System.out.println(" -h,  --help           Show this help message");
        System.out.println();
    }

    public String getSourceDirectory(){return sourceDirectory;}
    public String getOutputFormat(){return outputFormat;}
    public String getOutputFile(){return outputFile;}
    public boolean isVerbose(){return verbose;}
    public boolean isShowHelp(){return showHelp;}
}
