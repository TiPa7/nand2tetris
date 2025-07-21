@256
D=A
@SP
M=D //SP=256
@991c9e17-6baf-421b-851a-6997699991a4
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@5
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Sys.init
0;JMP //goto g (ending call g nargs)
(991c9e17-6baf-421b-851a-6997699991a4)
(Main.fibonacci) //function Main.fibonacci 0
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@2
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 2
@SP
AM=M-1
D=M
A=A-1
D=M-D
@LT_TRUE$c9065c44-b5fe-4ae0-bf42-dd618a6f089c
D;JLT
(LT_FALSE$c9065c44-b5fe-4ae0-bf42-dd618a6f089c)
@0
D=A
@SP
A=M-1
M=D
@END$c9065c44-b5fe-4ae0-bf42-dd618a6f089c
0;JMP
(LT_TRUE$c9065c44-b5fe-4ae0-bf42-dd618a6f089c)
D=-1
@SP
A=M-1
M=D
(END$c9065c44-b5fe-4ae0-bf42-dd618a6f089c) //lt
@SP
AM=M-1
D=M
@N_LT_2
D;JNE //if-goto N_LT_2
@N_GE_2
0;JMP //goto N_GE_2
(N_LT_2) //label N_LT_2
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
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
(N_GE_2) //label N_GE_2
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@2
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 2
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@6589e294-b3a2-4dc8-9861-1268552e8b4e
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@6
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Main.fibonacci
0;JMP //goto g (ending call g nargs)
(6589e294-b3a2-4dc8-9861-1268552e8b4e) //call Main.fibonacci 1
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@1
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@dac375f4-34bd-412c-b9d3-974f25a5238d
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@6
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Main.fibonacci
0;JMP //goto g (ending call g nargs)
(dac375f4-34bd-412c-b9d3-974f25a5238d) //call Main.fibonacci 1
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
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
(Sys.init) //function Sys.init 0
@4
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 4
@645f6488-faa3-403e-9800-4d392246f5a2
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@6
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Main.fibonacci
0;JMP //goto g (ending call g nargs)
(645f6488-faa3-403e-9800-4d392246f5a2) //call Main.fibonacci 1
(END) //label END
@END
0;JMP //goto END