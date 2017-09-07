package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hezd on 2017/9/6.
 */

public class FixHistoryResponse implements Serializable {

    /**
     * list : {"version":{"last_heart_time":{"ltime":"2分","lstate":1},"last_small":{"last_small_pla":"1.1.6","last_small_state":0},"new_small":"1.0.24"},"box_info":[{"rname":"新板卡测试","boxname":"V600","mac":"A0BB3ED24A5F","ustate":0,"last_heart_time":"无","ltime":"-888","repair_record":[]},{"rname":"望海楼","boxname":"望海楼1","mac":"FCD5D900B3B1","ustate":0,"last_heart_time":"无","ltime":"-888","repair_record":[]},{"rname":"雄起","boxname":"雄起","mac":"FCD5D930B4E5","ustate":0,"last_heart_time":"无","ltime":"-888","repair_record":[{"nickname":"Admin","ctime":"2017-09-05 10:21:49"},{"nickname":"Admin","ctime":"2017-09-05 10:34:03"},{"nickname":"Admin","ctime":"2017-09-05 10:34:11"},{"nickname":"Admin","ctime":"2017-09-05 10:34:58"},{"nickname":"Admin","ctime":"2017-09-05 14:45:58"},{"nickname":"Admin","ctime":"2017-09-05 14:51:01"},{"nickname":"Admin","ctime":"2017-09-05 14:55:29"},{"nickname":"Admin","ctime":"2017-09-05 15:03:09"},{"nickname":"Admin","ctime":"2017-09-05 15:03:10"},{"nickname":"Admin","ctime":"2017-09-05 15:03:11"},{"nickname":"Admin","ctime":"2017-09-05 15:03:31"},{"nickname":"Admin","ctime":"2017-09-05 15:03:45"}]},{"rname":"何竹冬","boxname":"何竹冬","mac":"FCD5D900B842","last_heart_time":"15天20小时","ltime":1503302402,"ustate":0,"repair_record":[]},{"rname":"小九修改","boxname":"xiaojiu5g","mac":"FCD5D900B18F","last_heart_time":"2分","ltime":1504671601,"ustate":1,"repair_record":[]},{"rname":"毕超","boxname":"bicao only","mac":"FCD5D900B6E4","last_heart_time":"2分","ltime":1504671601,"ustate":1,"repair_record":[]},{"rname":"测试","boxname":"ceshizhuanyong","mac":"FCD5D900B6A4","last_heart_time":"2分","ltime":1504671601,"ustate":1,"repair_record":[]}],"banwei":"版位信息(共29个,失联超过15个小时23个)"}
     */

    private PositionInfo list;

    public PositionInfo getList() {
        return list;
    }

    public void setList(PositionInfo list) {
        this.list = list;
    }

    public static class PositionInfo implements Serializable {
        /**
         * version : {"last_heart_time":{"ltime":"2分","lstate":1},"last_small":{"last_small_pla":"1.1.6","last_small_state":0},"new_small":"1.0.24"}
         * box_info : [{"rname":"新板卡测试","boxname":"V600","mac":"A0BB3ED24A5F","ustate":0,"last_heart_time":"无","ltime":"-888","repair_record":[]},{"rname":"望海楼","boxname":"望海楼1","mac":"FCD5D900B3B1","ustate":0,"last_heart_time":"无","ltime":"-888","repair_record":[]},{"rname":"雄起","boxname":"雄起","mac":"FCD5D930B4E5","ustate":0,"last_heart_time":"无","ltime":"-888","repair_record":[{"nickname":"Admin","ctime":"2017-09-05 10:21:49"},{"nickname":"Admin","ctime":"2017-09-05 10:34:03"},{"nickname":"Admin","ctime":"2017-09-05 10:34:11"},{"nickname":"Admin","ctime":"2017-09-05 10:34:58"},{"nickname":"Admin","ctime":"2017-09-05 14:45:58"},{"nickname":"Admin","ctime":"2017-09-05 14:51:01"},{"nickname":"Admin","ctime":"2017-09-05 14:55:29"},{"nickname":"Admin","ctime":"2017-09-05 15:03:09"},{"nickname":"Admin","ctime":"2017-09-05 15:03:10"},{"nickname":"Admin","ctime":"2017-09-05 15:03:11"},{"nickname":"Admin","ctime":"2017-09-05 15:03:31"},{"nickname":"Admin","ctime":"2017-09-05 15:03:45"}]},{"rname":"何竹冬","boxname":"何竹冬","mac":"FCD5D900B842","last_heart_time":"15天20小时","ltime":1503302402,"ustate":0,"repair_record":[]},{"rname":"小九修改","boxname":"xiaojiu5g","mac":"FCD5D900B18F","last_heart_time":"2分","ltime":1504671601,"ustate":1,"repair_record":[]},{"rname":"毕超","boxname":"bicao only","mac":"FCD5D900B6E4","last_heart_time":"2分","ltime":1504671601,"ustate":1,"repair_record":[]},{"rname":"测试","boxname":"ceshizhuanyong","mac":"FCD5D900B6A4","last_heart_time":"2分","ltime":1504671601,"ustate":1,"repair_record":[]}]
         * banwei : 版位信息(共29个,失联超过15个小时23个)
         */

        private VersionBean version;
        private String banwei;
        private List<BoxInfoBean> box_info;

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public String getBanwei() {
            return banwei;
        }

        public void setBanwei(String banwei) {
            this.banwei = banwei;
        }

        public List<BoxInfoBean> getBox_info() {
            return box_info;
        }

        public void setBox_info(List<BoxInfoBean> box_info) {
            this.box_info = box_info;
        }

        @Override
        public String toString() {
            return "DamageInfo{" +
                    "version=" + version +
                    ", banwei='" + banwei + '\'' +
                    ", box_info=" + box_info +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PositionInfo listBean = (PositionInfo) o;

            if (version != null ? !version.equals(listBean.version) : listBean.version != null)
                return false;
            if (banwei != null ? !banwei.equals(listBean.banwei) : listBean.banwei != null)
                return false;
            return box_info != null ? box_info.equals(listBean.box_info) : listBean.box_info == null;

        }

        @Override
        public int hashCode() {
            int result = version != null ? version.hashCode() : 0;
            result = 31 * result + (banwei != null ? banwei.hashCode() : 0);
            result = 31 * result + (box_info != null ? box_info.hashCode() : 0);
            return result;
        }

        public class VersionBean implements Serializable{
            /**
             * last_heart_time : {"ltime":"2分","lstate":1}
             * last_small : {"last_small_pla":"1.1.6","last_small_state":0}
             * new_small : 1.0.24
             */

            private LastHeartTimeBean last_heart_time;
            private LastSmallBean last_small;
            private String new_small;

            @Override
            public String toString() {
                return "VersionBean{" +
                        "last_heart_time=" + last_heart_time +
                        ", last_small=" + last_small +
                        ", new_small='" + new_small + '\'' +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                VersionBean that = (VersionBean) o;

                if (last_heart_time != null ? !last_heart_time.equals(that.last_heart_time) : that.last_heart_time != null)
                    return false;
                if (last_small != null ? !last_small.equals(that.last_small) : that.last_small != null)
                    return false;
                return new_small != null ? new_small.equals(that.new_small) : that.new_small == null;

            }

            @Override
            public int hashCode() {
                int result = last_heart_time != null ? last_heart_time.hashCode() : 0;
                result = 31 * result + (last_small != null ? last_small.hashCode() : 0);
                result = 31 * result + (new_small != null ? new_small.hashCode() : 0);
                return result;
            }

            public LastHeartTimeBean getLast_heart_time() {
                return last_heart_time;
            }

            public void setLast_heart_time(LastHeartTimeBean last_heart_time) {
                this.last_heart_time = last_heart_time;
            }

            public LastSmallBean getLast_small() {
                return last_small;
            }

            public void setLast_small(LastSmallBean last_small) {
                this.last_small = last_small;
            }

            public String getNew_small() {
                return new_small;
            }

            public void setNew_small(String new_small) {
                this.new_small = new_small;
            }

            public  class LastHeartTimeBean implements Serializable{
                /**
                 * ltime : 2分
                 * lstate : 1
                 */

                private String ltime;
                private int lstate;

                @Override
                public String toString() {
                    return "LastHeartTimeBean{" +
                            "ltime='" + ltime + '\'' +
                            ", lstate=" + lstate +
                            '}';
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;

                    LastHeartTimeBean that = (LastHeartTimeBean) o;

                    if (lstate != that.lstate) return false;
                    return ltime != null ? ltime.equals(that.ltime) : that.ltime == null;

                }

                @Override
                public int hashCode() {
                    int result = ltime != null ? ltime.hashCode() : 0;
                    result = 31 * result + lstate;
                    return result;
                }

                public String getLtime() {
                    return ltime;
                }

                public void setLtime(String ltime) {
                    this.ltime = ltime;
                }

                public int getLstate() {
                    return lstate;
                }

                public void setLstate(int lstate) {
                    this.lstate = lstate;
                }
            }

            public class LastSmallBean implements Serializable{
                /**
                 * last_small_pla : 1.1.6
                 * last_small_state : 0
                 */

                private String last_small_pla;
                private int last_small_state;

                @Override
                public String toString() {
                    return "LastSmallBean{" +
                            "last_small_pla='" + last_small_pla + '\'' +
                            ", last_small_state=" + last_small_state +
                            '}';
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;

                    LastSmallBean that = (LastSmallBean) o;

                    if (last_small_state != that.last_small_state) return false;
                    return last_small_pla != null ? last_small_pla.equals(that.last_small_pla) : that.last_small_pla == null;

                }

                @Override
                public int hashCode() {
                    int result = last_small_pla != null ? last_small_pla.hashCode() : 0;
                    result = 31 * result + last_small_state;
                    return result;
                }

                public String getLast_small_pla() {
                    return last_small_pla;
                }

                public void setLast_small_pla(String last_small_pla) {
                    this.last_small_pla = last_small_pla;
                }

                public int getLast_small_state() {
                    return last_small_state;
                }

                public void setLast_small_state(int last_small_state) {
                    this.last_small_state = last_small_state;
                }
            }
        }

        public class BoxInfoBean implements Serializable{
            public class RepaireInfo implements Serializable {
                private String nickname;
                private String ctime;

                @Override
                public String toString() {
                    return "RepaireInfo{" +
                            "nickname='" + nickname + '\'' +
                            ", ctime='" + ctime + '\'' +
                            '}';
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;

                    RepaireInfo that = (RepaireInfo) o;

                    if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null)
                        return false;
                    return ctime != null ? ctime.equals(that.ctime) : that.ctime == null;

                }

                @Override
                public int hashCode() {
                    int result = nickname != null ? nickname.hashCode() : 0;
                    result = 31 * result + (ctime != null ? ctime.hashCode() : 0);
                    return result;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getCtime() {
                    return ctime;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }
            }

            /**
             * rname : 新板卡测试
             * boxname : V600
             * mac : A0BB3ED24A5F
             * ustate : 0
             * last_heart_time : 无
             * ltime : -888
             * repair_record : []
             */

            private String rname;
            private String boxname;
            private String mac;
            private int ustate;
            private String last_heart_time;
            private String ltime;
            private List<RepaireInfo> repair_record;

            public String getRname() {
                return rname;
            }

            public void setRname(String rname) {
                this.rname = rname;
            }

            public String getBoxname() {
                return boxname;
            }

            public void setBoxname(String boxname) {
                this.boxname = boxname;
            }

            public String getMac() {
                return mac;
            }

            public void setMac(String mac) {
                this.mac = mac;
            }

            public int getUstate() {
                return ustate;
            }

            public void setUstate(int ustate) {
                this.ustate = ustate;
            }

            public String getLast_heart_time() {
                return last_heart_time;
            }

            public void setLast_heart_time(String last_heart_time) {
                this.last_heart_time = last_heart_time;
            }

            public String getLtime() {
                return ltime;
            }

            public void setLtime(String ltime) {
                this.ltime = ltime;
            }

            public List<RepaireInfo> getRepair_record() {
                return repair_record;
            }

            public void setRepair_record(List<RepaireInfo> repair_record) {
                this.repair_record = repair_record;
            }

            @Override
            public String toString() {
                return "BoxInfoBean{" +
                        "rname='" + rname + '\'' +
                        ", boxname='" + boxname + '\'' +
                        ", mac='" + mac + '\'' +
                        ", ustate=" + ustate +
                        ", last_heart_time='" + last_heart_time + '\'' +
                        ", ltime='" + ltime + '\'' +
                        ", repair_record=" + repair_record +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                BoxInfoBean that = (BoxInfoBean) o;

                if (ustate != that.ustate) return false;
                if (rname != null ? !rname.equals(that.rname) : that.rname != null) return false;
                if (boxname != null ? !boxname.equals(that.boxname) : that.boxname != null)
                    return false;
                if (mac != null ? !mac.equals(that.mac) : that.mac != null) return false;
                if (last_heart_time != null ? !last_heart_time.equals(that.last_heart_time) : that.last_heart_time != null)
                    return false;
                if (ltime != null ? !ltime.equals(that.ltime) : that.ltime != null) return false;
                return repair_record != null ? repair_record.equals(that.repair_record) : that.repair_record == null;

            }

            @Override
            public int hashCode() {
                int result = rname != null ? rname.hashCode() : 0;
                result = 31 * result + (boxname != null ? boxname.hashCode() : 0);
                result = 31 * result + (mac != null ? mac.hashCode() : 0);
                result = 31 * result + ustate;
                result = 31 * result + (last_heart_time != null ? last_heart_time.hashCode() : 0);
                result = 31 * result + (ltime != null ? ltime.hashCode() : 0);
                result = 31 * result + (repair_record != null ? repair_record.hashCode() : 0);
                return result;
            }
        }
    }
}
