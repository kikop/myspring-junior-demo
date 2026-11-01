package com.kikop.util;

import com.kikop.model.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaUtils {
    private void testLambdaDebug() {
        // 外部T:List<Integer>
        // 内部T:Integer
        List<List<Integer>> nestedList = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4, 5));
        List<Integer> collect = nestedList.stream()
                // 扁平化为流
                .flatMap(list -> list.stream())
                //.flatMap(List::stream)
                .collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
    }

    private List<UserInfo> buildUserInfoList() {
        List<String> permission1 = new ArrayList<>();
        permission1.addAll(Arrays.asList("a", "b"));

        List<String> permission2 = new ArrayList<>();
        permission2.addAll(Arrays.asList("c", "d"));

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setPermissions(permission1);

        UserInfo userInfo2 = new UserInfo();
        userInfo2.setPermissions(permission2);
        return Arrays.asList(userInfo1, userInfo2);
    }

    private void testLambdaDebug2() {
        List<UserInfo> userInfos = buildUserInfoList();
        List<String> collect = userInfos.stream().flatMap(userInfo -> userInfo.getPermissions().stream()).distinct().collect(Collectors.toList());
        collect.forEach(v -> System.out.println(v));
    }
}
