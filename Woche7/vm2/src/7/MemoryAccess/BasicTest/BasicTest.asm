@10
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 10
@0
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop local 0
@21
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 21
@22
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 22
@2
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop argument 2
@1
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop argument 1
@36
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 36
@6
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
M=D //pop this 6
@42
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 42