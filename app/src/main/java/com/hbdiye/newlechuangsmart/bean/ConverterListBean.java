package com.hbdiye.newlechuangsmart.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.List;

public class ConverterListBean implements Serializable {

    public String errcode;
    public List<InfraredEquipments> infraredEquipments;

    public class InfraredEquipments implements Serializable, IPickerViewData {

        public String equipmentCode;
        public String createTime;
        public String equipmentName;
        public int id;
        public String userId;
        public String getewayId;
        public String deviceInfraredRelations;
        public String mac;

        public DeviceListBean deviceListBean;

        @Override
        public String getPickerViewText() {
            return equipmentName;
        }
    }
}
