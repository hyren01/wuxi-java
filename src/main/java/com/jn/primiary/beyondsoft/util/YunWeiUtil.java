package com.jn.primiary.beyondsoft.util;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Timer;

@Component
public class YunWeiUtil {
    private static final int CPUTIME = 5000;
    private static final int PERIOD_TIME = 1000 * 60 * 15;
    private static final int SLEEP_TIME = 1000 * 60 * 9;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;
    private String isWindowsOrLinux = isWindowsOrLinux();
    private String pid = "";
    private Timer sysInfoGetTimer = new Timer("sysInfoGet");

    public YunWeiUtil(){
        if (isWindowsOrLinux.equals("windows")) { // 判断操作系统类型是否为：windows
            getJvmPIDOnWindows();
        } else {
            getJvmPIDOnLinux();
        }
    }

    /**
     * windows环境下获取JVM的PID
     */
    public void getJvmPIDOnWindows() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        pid = runtime.getName().split("@")[0];
    }

    /**
     * linux环境下获取JVM的PID
     */
    public void getJvmPIDOnLinux() {
        String command = "pidof java";
        BufferedReader in = null;
        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringTokenizer ts = new StringTokenizer(in.readLine());
            pid = ts.nextToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cmd cmd命令或者bat文件，bat文件获取系统权限时会有闪屏
     * 获取系统权限的方法
     *  @echo off
    %1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)
    cd /d "%~dp0"
    ipconfig
     * @return 命令输出内容
     *
     */
    public String executeCmd(String cmd) {
        String line = null;
        BufferedReader br=null;
        InputStream inputStream=null;
        StringBuffer buffer=new StringBuffer();
        try {
            Process proc = Runtime.getRuntime().exec(cmd);// 执行命令
            inputStream = proc.getInputStream();//执行结果 得到进程的标准输出信息流
            br = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
            while ((line = br.readLine()) != null) {
                buffer.append(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
                if(inputStream!=null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
     * 获取mac地址
     * @return
     */
    public String getLocalMac()  {
        StringBuffer sb = new StringBuffer();
        try {
            InetAddress ia = InetAddress.getLocalHost();
            byte[]    mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            for(int i=0; i<mac.length; i++) {
                if(i!=0) {
                    sb.append("-");
                }
                int temp = mac[i]&0xff;//字节转换为整数
                String str = Integer.toHexString(temp);
                if(str.length()==1) {
                    sb.append("0"+str);
                }else {
                    sb.append(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 获取操作系统类型
     * @return
     */
    public String getOsType()  {
        Properties props=System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        //String osArch = props.getProperty("os.arch"); //操作系统构架
        //String osVersion = props.getProperty("os.version"); //操作系统版本
        return osName;
    }

    /**
     * 获取本地IP
     * @return
     */
    public String getLocalIP(){
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()  && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        sb.append(inetAddress.getHostAddress().toString()+"\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取外网ip和所在地
     * @return
     */
    public String getRemoteIp() {
        InputStream in = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //             URL url = new URL("http://www.ip138.com/ip2city.asp"); //创建 URL
            URL url = new URL("http://ip.chinaz.com/getip.aspx"); //创建 URL
            in = url.openStream(); // 打开到这个URL的流
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String inputLine = "";
            while ((inputLine = reader.readLine()) != null)
            {
                buffer.append(inputLine);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
     * 获取最大可用磁盘
     * @return 返回  C D E F....
     */
    public String getMaxDisk()  {
        long size=0;
        String max="";
        File[] roots = File.listRoots();
        for (File file : roots) {
            if( file.getFreeSpace()>size) {
                size=file.getFreeSpace();
                max=file.getPath().substring(0, 1);
            }
            //                System.out.println("Free space = " + (file.getFreeSpace()/(1024*1024))/1024);  //显示GB大小
            //                System.out.println("Usable space = " + _file.getUsableSpace());
            //                System.out.println("Total space = " + _file.getTotalSpace());
            //                System.out.println("used space  = " + (_file.getTotalSpace()-_file.getFreeSpace()));
            //                System.out.println();
        }
        System.out.println(max);
        return max;
    }

    /**
     * 磁盘使用量
     * @return
     */
    public String getDiskUsage(){
        File diskPartition = new File("/");
        long allSpace = diskPartition.getTotalSpace();
        long usableSpace = diskPartition.getUsableSpace(); //可用剩余空间，单位为M
        String result = String.valueOf(allSpace - usableSpace);
        return result;
    }

    /**
     * 获取JVM 线程数
     *
     * @return
     */
    public int getThreadCount() {
        int threadCount = 0;
        if (isWindowsOrLinux.equals("windows")) { // 判断操作系统类型是否为：windows
            threadCount = getThreadCountForWindows();// 查询windows系统的线程数
        } else {
            threadCount = getThreadCountForLinux();// 查询linux系统的线程数
        }
        return threadCount;
    }

    /**
     * 获取当前运行路径
     * @return
     */
    public String  getPath() {
        File directory = new File("");//设定为当前文件夹File directory = new File("..")
        String path="";
        try{
            path=directory.getCanonicalPath();//获取标准的路径
            //path=directory.getAbsolutePath();//获取绝对路径
        }catch(Exception e){

        }
        return path;
    }

    /**
     * 获取内存使用率
     * @return
     */
    public String getMemeryUsage() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();// 总的物理内存+虚拟内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();    // 剩余的物理内存
        Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
        String str = "内存使用率:" + compare.intValue() + "%";
        return str;
    }

    /**
     * 获取内存使用量
     * @return
     */
    public String getMemery() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();// 总的物理内存+虚拟内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();    // 剩余的物理内存
        String str = String.valueOf(totalvirtualMemory-freePhysicalMemorySize);
        return str;
    }

    /**
     * 获取CPU使用率
     * @return
     */
    public String getCpuRatio() {
        try {
            String procCmd = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));    // 取进程信息
            Thread.sleep(200);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf((busytime) * 1.0 / (busytime + idletime)).intValue() + "%";
            } else {
                return  "0";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "CPU使用率:" + 0 + "%";
        }
    }

    private long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < 10) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                String s1 = substring(line, kmtidx, rocidx - 1).trim();
                String s2 = substring(line, umtidx, wocidx - 1).trim();
                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    if (s1.length() > 0)
                        idletime += Long.valueOf(s1).longValue();
                    if (s2.length() > 0)
                        idletime += Long.valueOf(s2).longValue();
                    continue;
                }
                if (s1.length() > 0)
                    kneltime += Long.valueOf(s1).longValue();
                if (s2.length() > 0)
                    usertime += Long.valueOf(s2).longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++) {
            tgt += (char) b[i];
        }
        return tgt;
    }

    /**
     * 判断是服务器的系统类型是Windows 还是 Linux
     *
     * @return
     */
    public String isWindowsOrLinux() {
        String osName = System.getProperty("os.name");
        String sysName = "";
        if (osName.toLowerCase().startsWith("windows")) {
            sysName = "windows";
        } else if (osName.toLowerCase().startsWith("linux")) {
            sysName = "linux";
        }
        return sysName;
    }

    public int getThreadCountForLinux() {
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        String command = "top -b -n 1 -H -p " + pid;
        BufferedReader in = null;
        int result = 0;
        try {
            pro = r.exec(new String[] { "sh", "-c", command });
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            in.readLine();
            StringTokenizer ts = new StringTokenizer(in.readLine());
            ts.nextToken();
            result = Integer.parseInt(ts.nextToken());
            in.close();
            pro.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 获取Windows环境下JVM的线程数
     *
     * @return
     */
    public int getThreadCountForWindows() {
        String command = "wmic process " + pid + " list brief";
        int count = 0;
        BufferedReader in = null;
        try {
            Process pro = Runtime.getRuntime().exec(command);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
// testGetInput(in);
            in.readLine();
            in.readLine();
            StringTokenizer ts = new StringTokenizer(in.readLine());
            int i = 1;

            while (ts.hasMoreTokens()) {
                i++;
                ts.nextToken();
                if (i == 5) {
                    count = Integer.parseInt(ts.nextToken());
                }
            }
            in.close();
            pro.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public String getHostName(){
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String host = ia.getHostName();
        return host;
    }

}
