cmake_minimum_required(VERSION 3.23)
project(15 C)

set(CMAKE_C_STANDARD 99)

add_executable(01 01.c)
add_executable(02 02.c)

file(COPY ${CMAKE_CURRENT_SOURCE_DIR}/search.dat DESTINATION ${CMAKE_CURRENT_BINARY_DIR})
file(COPY ${CMAKE_CURRENT_SOURCE_DIR}/welcome.dat DESTINATION ${CMAKE_CURRENT_BINARY_DIR})
