# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.15

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/goncalo/Downloads/clion-2019.3.3/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /home/goncalo/Downloads/clion-2019.3.3/bin/cmake/linux/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/goncalo/CLionProjects/aula1_CG

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/goncalo/CLionProjects/aula1_CG/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/CG1.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/CG1.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/CG1.dir/flags.make

CMakeFiles/CG1.dir/main.cpp.o: CMakeFiles/CG1.dir/flags.make
CMakeFiles/CG1.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/goncalo/CLionProjects/aula1_CG/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/CG1.dir/main.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/CG1.dir/main.cpp.o -c /home/goncalo/CLionProjects/aula1_CG/main.cpp

CMakeFiles/CG1.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/CG1.dir/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/goncalo/CLionProjects/aula1_CG/main.cpp > CMakeFiles/CG1.dir/main.cpp.i

CMakeFiles/CG1.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/CG1.dir/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/goncalo/CLionProjects/aula1_CG/main.cpp -o CMakeFiles/CG1.dir/main.cpp.s

# Object files for target CG1
CG1_OBJECTS = \
"CMakeFiles/CG1.dir/main.cpp.o"

# External object files for target CG1
CG1_EXTERNAL_OBJECTS =

CG1: CMakeFiles/CG1.dir/main.cpp.o
CG1: CMakeFiles/CG1.dir/build.make
CG1: /usr/lib/x86_64-linux-gnu/libGL.so
CG1: /usr/lib/x86_64-linux-gnu/libGLU.so
CG1: /usr/lib/x86_64-linux-gnu/libglut.so
CG1: /usr/lib/x86_64-linux-gnu/libXmu.so
CG1: /usr/lib/x86_64-linux-gnu/libXi.so
CG1: CMakeFiles/CG1.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/goncalo/CLionProjects/aula1_CG/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable CG1"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/CG1.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/CG1.dir/build: CG1

.PHONY : CMakeFiles/CG1.dir/build

CMakeFiles/CG1.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/CG1.dir/cmake_clean.cmake
.PHONY : CMakeFiles/CG1.dir/clean

CMakeFiles/CG1.dir/depend:
	cd /home/goncalo/CLionProjects/aula1_CG/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/goncalo/CLionProjects/aula1_CG /home/goncalo/CLionProjects/aula1_CG /home/goncalo/CLionProjects/aula1_CG/cmake-build-debug /home/goncalo/CLionProjects/aula1_CG/cmake-build-debug /home/goncalo/CLionProjects/aula1_CG/cmake-build-debug/CMakeFiles/CG1.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/CG1.dir/depend

