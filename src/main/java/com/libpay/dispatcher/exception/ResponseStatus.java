package com.libpay.dispatcher.exception;


import com.libpay.dispatcher.service.LibpayException;

public class ResponseStatus<T> {

        private int code=200;
        private String msg;
        private T data;


        public ResponseStatus(){

        }
        public ResponseStatus(LibpayException ex){
//            this.code=ex.code;
            this.msg=ex.getMessage();
        }

        /**
         * @return the code
         */
        public int getCode() {
            return code;
        }

        /**
         * @param code
         *            the code to set
         */
        public void setCode(int code) {
            this.code = code;
        }

        /**
         * @return the msg
         */
        public String getMsg() {
            return msg;
        }

        /**
         * @param msg
         *            the msg to set
         */
        public void setMsg(String msg) {
            this.msg = msg;
        }

        /**
         * @return the data
         */
        public T getData() {
            return data;
        }

        /**
         * @param data
         *            the data to set
         */
        public void setData(T data) {
            this.data = data;
        }
        public static enum ErrorCode{
            SYS_ERROR(500),INPUT_ERROR(100);
            int code;
            ErrorCode(int code){
                this.code=code;
            }
            /**
             * @return the code
             */
            public int getCode() {
                return code;
            }

        }
		@Override
		public String toString() {
			return "ResponseStatus [code=" + code + ", msg=" + msg + ", data=" + data + "]";
		}


}
