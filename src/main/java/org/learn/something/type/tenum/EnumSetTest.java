package org.learn.something.type.tenum;

import java.util.EnumSet;
import java.util.Set;

/**
 * 使用 EnumSet 而不是使用 位域
 */
public class EnumSetTest {

    public enum Permission {
        READ,
        WRITE,
        EXECUTE,
        DELETE,
        UPDATE;
    }

    public static class User {

        private Set<Permission> permissions;

        public void applyPermission(Set<Permission> permissions) {
            this.permissions = permissions;
        }

        public void addPermission(Permission permission) {
            permissions.add(permission);
        }

        public void removePermission(Permission permission) {
            permissions.remove(permission);
        }

        public void printHavePermissions() {
            System.out.println(permissions);
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.applyPermission(EnumSet.of(Permission.EXECUTE, Permission.READ));
        user.printHavePermissions();
    }
}
