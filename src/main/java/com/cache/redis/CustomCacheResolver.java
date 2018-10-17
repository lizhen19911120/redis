package com.cache.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by lizhen on 2018/10/16.
 */
@Deprecated
public class CustomCacheResolver implements CacheResolver {

    private final CacheManager cacheManager;

    public CustomCacheResolver(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        Collection<? extends Cache> caches = getCaches(cacheManager, context);
        return caches;
    }


    private Collection<? extends Cache> getCaches(CacheManager cacheManager, CacheOperationInvocationContext<?> context) {
        return context.getOperation().getCacheNames().stream()
                .map(cacheName -> cacheManager.getCache(cacheName))
                .filter(cache -> cache != null)
                .collect(Collectors.toList());
    }
}
