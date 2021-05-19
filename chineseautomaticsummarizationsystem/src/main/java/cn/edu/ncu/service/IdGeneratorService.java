package cn.edu.ncu.service;

import cn.edu.ncu.common.constant.IdGeneratorEnum;
import java.util.List;

/**
 * 全局id生成器
 * zhuo
 */
public interface IdGeneratorService {



     String generate(IdGeneratorEnum idGeneratorEnum) ;

    /**
     * @param idGeneratorEnum
     * @param stepLength
     * @return
     */
     List<String> generate(IdGeneratorEnum idGeneratorEnum, int stepLength) ;



}
