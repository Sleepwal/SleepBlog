package com.sleep.domain.vo;

import com.sleep.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/23 18:32
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;
}