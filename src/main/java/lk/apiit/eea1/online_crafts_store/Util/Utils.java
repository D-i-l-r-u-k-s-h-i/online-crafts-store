package lk.apiit.eea1.online_crafts_store.Util;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.RoleName;
import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    private Utils() {
    }

    //using generics
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public static RoleName getUserRole(){
        UserSession user = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (RoleName role : RoleName.values()){
            Object obj = user.getRole().getRoleName();
            if (role.toString().equals(obj.toString())){
                return role;
            }
        }
        return null;
    }

    // Check claims inside token
    public static boolean checkToken(String token) throws Exception {
        if (token == null || token.isEmpty()) {
            throw new IllegalAccessException("Authentication failed.");
        }
        return true;
    }
}
