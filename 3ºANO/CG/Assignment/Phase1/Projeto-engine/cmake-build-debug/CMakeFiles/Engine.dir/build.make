# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.16

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
CMAKE_SOURCE_DIR = /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Engine.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Engine.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Engine.dir/flags.make

CMakeFiles/Engine.dir/main.cpp.o: CMakeFiles/Engine.dir/flags.make
CMakeFiles/Engine.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Engine.dir/main.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Engine.dir/main.cpp.o -c /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/main.cpp

CMakeFiles/Engine.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Engine.dir/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/main.cpp > CMakeFiles/Engine.dir/main.cpp.i

CMakeFiles/Engine.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Engine.dir/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/main.cpp -o CMakeFiles/Engine.dir/main.cpp.s

CMakeFiles/Engine.dir/tinyxml2.cpp.o: CMakeFiles/Engine.dir/flags.make
CMakeFiles/Engine.dir/tinyxml2.cpp.o: ../tinyxml2.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/Engine.dir/tinyxml2.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Engine.dir/tinyxml2.cpp.o -c /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/tinyxml2.cpp

CMakeFiles/Engine.dir/tinyxml2.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Engine.dir/tinyxml2.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/tinyxml2.cpp > CMakeFiles/Engine.dir/tinyxml2.cpp.i

CMakeFiles/Engine.dir/tinyxml2.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Engine.dir/tinyxml2.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/tinyxml2.cpp -o CMakeFiles/Engine.dir/tinyxml2.cpp.s

CMakeFiles/Engine.dir/OurXml.cpp.o: CMakeFiles/Engine.dir/flags.make
CMakeFiles/Engine.dir/OurXml.cpp.o: ../OurXml.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/Engine.dir/OurXml.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Engine.dir/OurXml.cpp.o -c /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/OurXml.cpp

CMakeFiles/Engine.dir/OurXml.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Engine.dir/OurXml.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/OurXml.cpp > CMakeFiles/Engine.dir/OurXml.cpp.i

CMakeFiles/Engine.dir/OurXml.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Engine.dir/OurXml.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/OurXml.cpp -o CMakeFiles/Engine.dir/OurXml.cpp.s

CMakeFiles/Engine.dir/Our3d.cpp.o: CMakeFiles/Engine.dir/flags.make
CMakeFiles/Engine.dir/Our3d.cpp.o: ../Our3d.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object CMakeFiles/Engine.dir/Our3d.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Engine.dir/Our3d.cpp.o -c /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/Our3d.cpp

CMakeFiles/Engine.dir/Our3d.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Engine.dir/Our3d.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/Our3d.cpp > CMakeFiles/Engine.dir/Our3d.cpp.i

CMakeFiles/Engine.dir/Our3d.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Engine.dir/Our3d.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/Our3d.cpp -o CMakeFiles/Engine.dir/Our3d.cpp.s

# Object files for target Engine
Engine_OBJECTS = \
"CMakeFiles/Engine.dir/main.cpp.o" \
"CMakeFiles/Engine.dir/tinyxml2.cpp.o" \
"CMakeFiles/Engine.dir/OurXml.cpp.o" \
"CMakeFiles/Engine.dir/Our3d.cpp.o"

# External object files for target Engine
Engine_EXTERNAL_OBJECTS =

Engine: CMakeFiles/Engine.dir/main.cpp.o
Engine: CMakeFiles/Engine.dir/tinyxml2.cpp.o
Engine: CMakeFiles/Engine.dir/OurXml.cpp.o
Engine: CMakeFiles/Engine.dir/Our3d.cpp.o
Engine: CMakeFiles/Engine.dir/build.make
Engine: /usr/lib/x86_64-linux-gnu/libGL.so
Engine: /usr/lib/x86_64-linux-gnu/libGLU.so
Engine: /usr/lib/x86_64-linux-gnu/libglut.so
Engine: /usr/lib/x86_64-linux-gnu/libXmu.so
Engine: /usr/lib/x86_64-linux-gnu/libXi.so
Engine: CMakeFiles/Engine.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Linking CXX executable Engine"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Engine.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Engine.dir/build: Engine

.PHONY : CMakeFiles/Engine.dir/build

CMakeFiles/Engine.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Engine.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Engine.dir/clean

CMakeFiles/Engine.dir/depend:
	cd /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug /home/goncalo/Documentos/3ANO/CG/PROJECTO/1_Fase/Projeto-engine/cmake-build-debug/CMakeFiles/Engine.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Engine.dir/depend

