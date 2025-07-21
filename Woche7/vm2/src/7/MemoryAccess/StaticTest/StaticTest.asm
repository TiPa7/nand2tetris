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
@t.8
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 8
@i.3
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 3
@c.1
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 1
@T.3
D=M
@SP
A=M+1
A=A-1
M=D
@SP
AM=M+1
A=A-1
M=D //push static 3
@e.1
D=M
@SP
A=M+1
A=A-1
M=D
@SP
AM=M+1
A=A-1
M=D //push static 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@t.8
D=M
@SP
A=M+1
A=A-1
M=D
@SP
AM=M+1
A=A-1
M=D //push static 8