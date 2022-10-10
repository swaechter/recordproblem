# Record Problem

## Problem

This repository provides a small Micronaut web application that simulates a JWT login endpoint. The application only works, when no native-image configuration is created via GraalVM agent (In my real application, my login code relies on the generated agent output because this code uses reflection). As soon the GraalVM agent with the following command:

```bash
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image/ch.swaechter/recordproblem -jar build/libs/recordproblem-all.jar
```

is executed (+a HTTP call to `http://localhost:8080/login`), the native compilation fails with the following error:

```
system@system:~/Downloads/recordproblem$ ./gradlew nativeCompile

> Task :generateResourcesConfigFile
[native-image-plugin] Resources configuration written into /home/system/Downloads/recordproblem/build/native/generated/generateResourcesConfigFile/resource-config.json

> Task :nativeCompile
[native-image-plugin] Toolchain detection is disabled, will use GraalVM from /usr/lib/jvm/graalvm/.
[native-image-plugin] Using executable path: /usr/lib/jvm/graalvm/bin/native-image
Warning: Using a deprecated option --allow-incomplete-classpath from 'META-INF/native-image/io.micronaut/micronaut-inject/native-image.properties' in 'file:///home/system/.gradle/caches/modules-2/files-2.1/io.micronaut/micronaut-inject/3.7.1/9e10b783728b60489d449404cfc58d4f506821f3/micronaut-inject-3.7.1.jar'. Allowing an incomplete classpath is now the default. Use --link-at-build-time to report linking errors at image build time for a class or package.
WARNING: Unknown module: org.graalvm.nativeimage.llvm specified to --add-exports
WARNING: Unknown module: org.graalvm.nativeimage.llvm specified to --add-exports
WARNING: Unknown module: org.graalvm.nativeimage.llvm specified to --add-exports
========================================================================================================================
GraalVM Native Image: Generating 'recordproblem' (executable)...
========================================================================================================================
[1/7] Initializing...                                                                                    (3.9s @ 0.12GB)
 Version info: 'GraalVM 22.2.0 Java 17 CE'
 Java version info: '17.0.4+8-jvmci-22.2-b06'
 C compiler: gcc (linux, x86_64, 10.2.1)
 Garbage collector: Serial GC
 4 user-specific feature(s)
 - io.micronaut.buffer.netty.NettyFeature
 - io.micronaut.core.graal.ServiceLoaderFeature
 - io.micronaut.http.netty.graal.HttpNettyFeature
 - io.micronaut.jackson.JacksonDatabindFeature
[2/7] Performing analysis...  [**********]                                                              (21.4s @ 0.97GB)
  13,655 (92.20%) of 14,810 classes reachable
  18,862 (56.50%) of 33,382 fields reachable
  66,643 (61.42%) of 108,504 methods reachable
     857 classes,   370 fields, and 3,008 methods registered for reflection
      64 classes,    76 fields, and    55 methods registered for JNI access
       4 native libraries: dl, pthread, rt, z
[3/7] Building universe...                                                                               (2.7s @ 3.16GB)
[4/7] Parsing methods...      [*]                                                                        (1.7s @ 3.39GB)
[5/7] Inlining methods...     [***]                                                                      (1.0s @ 1.67GB)
[6/7] Compiling methods...    [***]                                                                     (11.6s @ 0.96GB)

[7/7] Creating image...                                                                                  (0.0s @ 2.00GB)
Fatal error: com.oracle.svm.core.util.VMError$HostedError: com.oracle.svm.core.util.VMError$HostedError: New Method or Constructor found as reachable after static analysis: public java.lang.String ch.swaechter.components.account.dto.TokenDto.token()
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.util.VMError.shouldNotReachHere(VMError.java:72)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:682)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.run(NativeImageGenerator.java:521)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.buildImage(NativeImageGeneratorRunner.java:407)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.build(NativeImageGeneratorRunner.java:585)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.main(NativeImageGeneratorRunner.java:128)
Caused by: com.oracle.svm.core.util.VMError$HostedError: New Method or Constructor found as reachable after static analysis: public java.lang.String ch.swaechter.components.account.dto.TokenDto.token()
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.util.VMError.shouldNotReachHere(VMError.java:68)
        at org.graalvm.nativeimage.builder/com.oracle.svm.reflect.hosted.ReflectionFeature.getOrCreateAccessor(ReflectionFeature.java:109)
        at org.graalvm.nativeimage.builder/com.oracle.svm.reflect.hosted.ExecutableAccessorComputer.compute(ExecutableAccessorComputer.java:50)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.substitute.ComputedValueField.computeValue(ComputedValueField.java:384)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.substitute.ComputedValueField.readValue(ComputedValueField.java:353)
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.meta.ReadableJavaField.readFieldValue(ReadableJavaField.java:38)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.ameta.AnalysisConstantReflectionProvider.readValue(AnalysisConstantReflectionProvider.java:102)
------------------------------------------------------------------------------------------------------------------------
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.meta.HostedField.readValue(HostedField.java:161)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.image.NativeImageHeap.addObjectToImageHeap(NativeImageHeap.java:439)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.image.NativeImageHeap.addObject(NativeImageHeap.java:295)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.image.NativeImageHeap.processAddObjectWorklist(NativeImageHeap.java:598)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.image.NativeImageHeap.addTrailingObjects(NativeImageHeap.java:198)
        at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:664)
        ... 4 more
                        2.7s (5.8% of total time) in 33 GCs | Peak RSS: 6.04GB | CPU load: 5.58
========================================================================================================================
Failed generating 'recordproblem' after 46.5s.
    Error: Image build request failed with exit status 1

> Task :nativeCompile FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':nativeCompile'.
> Process 'command '/usr/lib/jvm/graalvm/bin/native-image'' finished with non-zero exit value 1

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 47s
5 actionable tasks: 4 executed, 1 up-to-date
system@system:~/Downloads/recordproblem$ 
```

Probably due to the generated code in `src/main/resources/META-INF/native-image/ch.swaechter/recordproblem/reflect-config.json`:

```
{
  "name":"ch.swaechter.components.account.dto.$TokenDto$IntrospectionRef",
  "methods":[{"name":"<init>","parameterTypes":[] }]
},
{
  "name":"ch.swaechter.components.account.dto.TokenDto",
  "allDeclaredFields":true,
  "queryAllDeclaredMethods":true,
  "queryAllDeclaredConstructors":true
},
```

The problem is, that I **have** to use the GraalVM agent to trace the relfection code in my real `AccountService.login()` method.

## Usage

### Step 1: Ensure GraalVM is used

```bash
system@system:~/Downloads/recordproblem$ export GRAALVM_HOME=/usr/lib/jvm/graalvm/
system@system:~/Downloads/recordproblem$ export PATH=/usr/lib/jvm/graalvm/bin/:$PAT
system@system:~/Downloads/recordproblem$ java -version
openjdk version "17.0.4" 2022-07-19
OpenJDK Runtime Environment GraalVM CE 22.2.0 (build 17.0.4+8-jvmci-22.2-b06)
OpenJDK 64-Bit Server VM GraalVM CE 22.2.0 (build 17.0.4+8-jvmci-22.2-b06, mixed mode, sharing)
```

### Step 2: Delete the generated GraalVM config

```bash
 rm -rf src/main/resources/META-INF/
```

### Step 3: Compile the application and run it

```bash
./gradlew nativeCompile
/home/system/Downloads/recordproblem/build/native/nativeCompile/recordproblem

# In a new console:
curl http://localhost:8080/login
# Here my real application generates an exception
```

### Step 4: Restore the agent output, rebuild and trigger the error

Stop the application and restore the agent output (or just re-generate it with the command):

```bash
git reset --hard HEAD
```

Now rebuild the application via `./gradlew nativeCompile` to trigger the initial error

## Step 5: Apply the workaround

Like discussed in https://github.com/oracle/graal/discussions/4670, one can manually call the record attributes to fix the native-image creation. So enable the code in `src/main/ch/swaechter/components/graalvm/GraalVmService.java`:

```java
package ch.swaechter.components.graalvm;

import ch.swaechter.components.account.dto.TokenDto;
import io.micronaut.context.annotation.Context;

@Context
public class GraalVmService {

    /**
     * Register all records
     */
    public GraalVmService() throws Exception {
        TokenDto tokenDto = new TokenDto("unused");
        TokenDto.class.getDeclaredMethod("token").invoke(tokenDto);
    }
}
```

Afterwards the native-image creation will works - I just can't manually call over 150 records...



