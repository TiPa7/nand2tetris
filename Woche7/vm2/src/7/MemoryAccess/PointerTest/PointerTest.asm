@3030
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 3030
@0
D=A
@3
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop pointer 0
@3040
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 3040
@1
D=A
@3
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop pointer 1
@32
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 32
@2
D=A
@THIS
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop this 2
@46
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 46
@6
D=A
@THAT
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop that 6
@3
D=A
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push pointer 0
@3
D=A
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push pointer 1
@SP
AM=M-1
D=M
A=A-1
M=M+D //add