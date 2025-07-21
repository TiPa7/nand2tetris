(SimpleFunction.test)
D=0
@SP
AM=M+1
A=A-1
M=D
D=0
@SP
AM=M+1
A=A-1
M=D //function SimpleFunction.test 2
@LCL
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 0
@LCL
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 1
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@SP
A=M-1
M=!M //not
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@ARG
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@LCL
D=M
@R13
M=D //frame=LCL
@5
A=D-A
D=M
@R14
M=D //retrAddr = *(frame-5)
@SP
AM=M-1
D=M
@ARG
A=M
M=D //*ARG = pop
@ARG
D=M+1
@SP
M=D //SP=ARG+1
@R13
AM=M-1
D=M
@THAT
M=D //THAT=*(frame-1)
@R13
AM=M-1
D=M
@THIS
M=D //THIS=*(frame-2)
@R13
AM=M-1
D=M
@ARG
M=D //ARG=*(frame-3)
@R13
AM=M-1
D=M
@LCL
M=D //LCL=*(frame-4)
@R14
A=M
0;JMP //goto retrAddr //return