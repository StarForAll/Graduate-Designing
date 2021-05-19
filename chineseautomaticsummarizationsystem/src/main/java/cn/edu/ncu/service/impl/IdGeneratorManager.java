package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.core.exception.BusinessException;
import cn.edu.ncu.dao.mapper.IdGeneratorMapper;
import cn.edu.ncu.pojo.dto.IdGeneratorLastNumberDTO;
import cn.edu.ncu.pojo.dto.IdGeneratorPOJO;
import cn.edu.ncu.service.exception.BaseDataExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * 全局id生成器
 * zhuo
 */
@Service
public class IdGeneratorManager {

    @Resource
    private IdGeneratorMapper idGeneratorMapper;

    @Transactional(rollbackFor = Exception.class)
    public long[] generate(IdGeneratorPOJO idGeneratorPOJO, int stepLength) {
        IdGeneratorLastNumberDTO idGeneratorLastNumberDTO = idGeneratorMapper.selectLastNumber(idGeneratorPOJO.getIdGeneratorEntity().getId());
        if (idGeneratorLastNumberDTO == null) {
            throw new BusinessException(BaseDataExceptionCode.ID_GENERATOR_CODE,
                    new Exception("IdGenerator， id 数据库不存在" + idGeneratorPOJO.getIdGeneratorEntity().getId()));
        }

        Long lastNumber = idGeneratorLastNumberDTO.getLastNumber();
        if (lastNumber == null) {
            lastNumber = idGeneratorPOJO.getIdGeneratorEntity().getInitNumber();
        } else {
            lastNumber = lastNumber + 1;
        }

        Date updateTime = idGeneratorLastNumberDTO.getUpdateTime();
        if (updateTime == null) {
            updateTime = idGeneratorLastNumberDTO.getDatabaseTime();
        }

        Long startValue = -1L, endValue = -1L;
        switch (idGeneratorPOJO.getIdGeneratorRuleTypeEnum()) {
            case NO_CYCLE:
                startValue = lastNumber.longValue();
                endValue = startValue + stepLength;
                break;
            default:
                SimpleDateFormat format = new SimpleDateFormat(idGeneratorPOJO.getIdGeneratorRuleTypeEnum().getExt());
                if (format.format(idGeneratorLastNumberDTO.getDatabaseTime()).equals(format.format(updateTime))) {
                    startValue = lastNumber.longValue();
                    endValue = startValue + stepLength;
                } else {
                    startValue = idGeneratorPOJO.getIdGeneratorEntity().getInitNumber();
                    endValue = startValue + stepLength;
                }
                break;
        }

        idGeneratorMapper.updateLastNumber(idGeneratorPOJO.getIdGeneratorEntity().getId(), endValue - 1);
        LocalDate localDate = LocalDate.now();
        idGeneratorMapper.replaceIdGeneratorRecord(idGeneratorPOJO.getIdGeneratorEntity().getId(), localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), endValue - 1);
        return new long[]{startValue, endValue};
    }
}
