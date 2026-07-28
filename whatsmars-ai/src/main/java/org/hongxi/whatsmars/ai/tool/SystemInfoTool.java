package org.hongxi.whatsmars.ai.tool;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * 系统信息工具
 * <p>
 * LLM 无法获取当前运行时的系统环境信息（JVM 版本、内存、操作系统等）。
 * 通过此工具可以查看应用运行环境，辅助问题排查和环境诊断。
 * </p>
 *
 * @author hongxi
 */
@Component
public class SystemInfoTool {

    /**
     * 获取当前系统环境信息（JVM、内存、操作系统）
     */
    @Tool(name = "get_system_info", value = "获取当前应用运行环境信息，包括 Java 版本、JVM 内存、操作系统、系统负载等")
    public String getSystemInfo() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();

        Runtime rt = Runtime.getRuntime();
        long maxMem = rt.maxMemory() / (1024 * 1024);
        long totalMem = rt.totalMemory() / (1024 * 1024);
        long freeMem = rt.freeMemory() / (1024 * 1024);
        long usedMem = totalMem - freeMem;

        return """
                === 系统环境信息 ===
                
                【Java】
                  版本: %s
                  供应商: %s
                  JVM 名称: %s
                  启动参数: %s
                
                【内存】
                  最大堆内存: %d MB
                  已分配堆内存: %d MB
                  已使用: %d MB
                  可用: %d MB
                
                【操作系统】
                  名称: %s
                  架构: %s
                  可用处理器: %d
                  系统负载: %.2f
                """.formatted(
                System.getProperty("java.version"),
                System.getProperty("java.vendor"),
                runtime.getVmName(),
                runtime.getInputArguments(),
                maxMem, totalMem, usedMem, maxMem - usedMem,
                os.getName(), os.getArch(), os.getAvailableProcessors(),
                os.getSystemLoadAverage()
        );
    }

    /**
     * 获取当前环境变量信息
     */
    @Tool(name = "get_environment_info", value = "获取当前系统的关键环境变量，如 PATH、HOME、USER 等")
    public String getEnvironmentInfo() {
        StringBuilder sb = new StringBuilder("=== 环境变量 ===\n\n");
        String[] keys = {"USER", "HOME", "SHELL", "PATH", "JAVA_HOME", "OPENAI_API_KEY"};
        for (String key : keys) {
            String value = System.getenv(key);
            if (value != null) {
                // 对敏感信息做脱敏处理
                if (key.equals("PATH") && value.length() > 100) {
                    value = value.substring(0, 100) + "... (已截断)";
                } else if (key.equals("OPENAI_API_KEY")) {
                    value = value.substring(0, Math.min(4, value.length())) + "****";
                }
                sb.append("  ").append(key).append(": ").append(value).append("\n");
            } else {
                sb.append("  ").append(key).append(": (未设置)\n");
            }
        }
        return sb.toString();
    }
}
