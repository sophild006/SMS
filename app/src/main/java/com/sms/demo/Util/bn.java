package com.sms.demo.Util;

/* compiled from: ZeroSms */
public class bn {
    public final Object Code;
    public final Object f7097V;

    public bn(Object obj, Object obj2) {
        this.Code = obj;
        this.f7097V = obj2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof bn)) {
            return false;
        }
        try {
            bn bnVar = (bn) obj;
            if (this.Code.equals(bnVar.Code) && this.f7097V.equals(bnVar.f7097V)) {
                return true;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return ((this.Code.hashCode() + 527) * 31) + this.f7097V.hashCode();
    }
}