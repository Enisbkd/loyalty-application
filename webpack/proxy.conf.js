function setupProxy({ tls }) {
  const serverResources = ['/api', '/services', '/management', '/v3/api-docs', '/h2-console', '/health'];
  return [
    {
      context: serverResources,
      target: `http${tls ? 's' : ''}://localhost:6060`,
      secure: false,
      changeOrigin: tls,
    },
  ];
}

module.exports = setupProxy;
