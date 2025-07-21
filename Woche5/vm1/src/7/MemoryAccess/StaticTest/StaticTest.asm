@111
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 111
@333
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 333
@888
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 888
@8
D=A
@16
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 8
@3
D=A
@16
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 3
@1
D=A
@16
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 1
@16
D=M
@3
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push static 3
@16
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push static 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@16
D=M
@8
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push static 8
@SP
AM=M-1
D=M
A=A-1
M=M+D //add