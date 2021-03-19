/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.jishnuc;

import org.openjdk.jmh.annotations.Benchmark;

public class MyBenchmark {

    @Benchmark
    public void testMethod() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        Address  address= new Address("udp:acr04.pleasanton.ca.sfba.comcast.net/161");
        getIpfromAddressOld(address);
    }

    @Benchmark
    public void testMethod2() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        Address  address=  new Address("udp:acr04.pleasanton.ca.sfba.comcast.net/161");
        getIpAddressFromTransportAddress(address);
    }


    public String getIpfromAddressOld(Address transportAddress){
        String ipAddressStr = transportAddress.toString().replace("/161", "");
        String ip = replaceSeperator(ipAddressStr);
        return createEvenLengthIp(ip);

    }
    private String createEvenLengthIp(String ip) {
        String updatedIp = ip;
        if(ip.length()%2 != 0) {
            updatedIp = "0"+updatedIp;
        }
        return updatedIp;
    }

    private String replaceSeperator(String ip) {
        String updatedIp = ip;
        if(ip.contains(":")) {
            updatedIp = ip.replaceAll(":", "");
        }
        if(ip.contains(".")) {
            updatedIp = ip.replaceAll("\\.", "");
        }
        return updatedIp;
    }

    private String getIpAddressFromTransportAddress(Address transportAddress) {
        //removes udp port from address
        String ipAddressStr=transportAddress.toString();
        if(ipAddressStr.endsWith("/161")){
            int lastIndexOf=ipAddressStr.lastIndexOf("/161");
            char[] chars=ipAddressStr.toCharArray();
            int specialCharCount=0;
            for(int i=0;i<chars.length;i++){
                if(chars[i]==':' || chars[i]=='.'){
                    specialCharCount++;
                }
            }
            int newArrayLength=lastIndexOf-specialCharCount;
            char[] newChar=new char[newArrayLength];
            int newIndex=0;
            if(newArrayLength%2!=0){
                newArrayLength++;
                newChar=new char[newArrayLength];
                newChar[0]='0';
                newIndex=1;
            }
            for(int i=0;i<lastIndexOf;i++){
                if(chars[i]!=':' && chars[i]!='.'){
                    newChar[newIndex++]=chars[i];
                }
            }
            return String.valueOf(newChar);
        }
        return ipAddressStr;
    }

}
